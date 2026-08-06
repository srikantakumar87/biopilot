package io.github.srikantakumar87.biopilot.feature.camera.heartrate

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.srikantakumar87.biopilot.R
import io.github.srikantakumar87.biopilot.core.audio.AudioPlayer
import io.github.srikantakumar87.biopilot.core.camera.FingerDetector
import io.github.srikantakumar87.biopilot.core.camera.HRVCalculator
import io.github.srikantakumar87.biopilot.core.camera.HeartRateCalculator
import io.github.srikantakumar87.biopilot.core.camera.MeasurementTimer
import io.github.srikantakumar87.biopilot.core.camera.PPGSignalProcessor
import io.github.srikantakumar87.biopilot.core.camera.PeakDetector
import io.github.srikantakumar87.biopilot.core.camera.RRIntervalCalculator
import io.github.srikantakumar87.biopilot.core.camera.SignalQualityAnalyzer
import io.github.srikantakumar87.biopilot.core.camera.StabilizationTimer
import io.github.srikantakumar87.biopilot.core.camera.model.MeasurementState
import io.github.srikantakumar87.biopilot.core.camera.model.PPGSample
import io.github.srikantakumar87.biopilot.core.camera.model.SignalQuality
import io.github.srikantakumar87.biopilot.core.camera.model.WavePoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class CameraHeartRateViewModel @Inject constructor(

    private val signalProcessor: PPGSignalProcessor,
    private val peakDetector: PeakDetector,
    private val heartRateCalculator: HeartRateCalculator,
    private val signalQualityAnalyzer: SignalQualityAnalyzer,
    private val fingerDetector: FingerDetector,
    private val measurementTimer: MeasurementTimer,
    private val stabilizationTimer: StabilizationTimer,
    private val audioPlayer: AudioPlayer,
    private val rrIntervalCalculator: RRIntervalCalculator,
    private val hrvCalculator: HRVCalculator,

    ) : ViewModel() {

    private val _uiState =
        MutableStateFlow(CameraHeartRateUiState())

    val uiState = _uiState.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        signalProcessor.clear()
        measurementTimer.reset()
    }

    fun onFrame(sample: PPGSample) {

        // Always show RGB values
        _uiState.value = _uiState.value.copy(
            red = sample.red,
            green = sample.green,
            blue = sample.blue
        )

        // No finger
        if (!fingerDetector.hasFinger(sample)) {
            handleNoFinger()
            return
        }

        processMeasurement(sample)
    }

    private fun handleNoFinger() {

        if (_uiState.value.measurementState == MeasurementState.STABILIZING ||
            _uiState.value.measurementState == MeasurementState.MEASURING ||
            _uiState.value.measurementState == MeasurementState.PROCESSING
        ) {
            audioPlayer.play(R.raw.error)
        }

        signalProcessor.clear()
        measurementTimer.reset()

        _uiState.value = _uiState.value.copy(

            measurementState = MeasurementState.WAITING_FOR_FINGER,

            waveform = emptyList(),

            bpm = null,

            measurementProgress = 0f,

            elapsedSeconds = 0,

            remainingSeconds = 30,

            sampleCount = 0,

            peakCount = 0,

            signalQuality = SignalQuality.NO_SIGNAL
        )

        stabilizationTimer.reset()

        measurementTimer.reset()


        signalProcessor.clear()
    }

    private fun processMeasurement(
        sample: PPGSample
    ) {

        val processedSample =
            signalProcessor.addSample(sample)

        val signal =
            signalProcessor.getSignal()

        val quality =
            signalQualityAnalyzer.analyze(signal)

        // Wait until signal is good before starting timer
        if (!measurementTimer.isRunning()) {

            if (quality == SignalQuality.GOOD ||
                quality == SignalQuality.EXCELLENT
            ) {

                if (!stabilizationTimer.isRunning()) {

                    stabilizationTimer.start()

                    _uiState.value = _uiState.value.copy(

                        measurementState =
                            MeasurementState.STABILIZING,

                        signalQuality = quality
                    )

                    return
                }

                if (!stabilizationTimer.finished()) {

                    _uiState.value = _uiState.value.copy(

                        measurementState =
                            MeasurementState.STABILIZING,

                        signalQuality = quality
                    )

                    return
                }


                stabilizationTimer.reset()

                measurementTimer.start()

                audioPlayer.play(
                    R.raw.measurement_started
                )

            } else {

                stabilizationTimer.reset()

                _uiState.value = _uiState.value.copy(

                    measurementState =
                        MeasurementState.WAITING_FOR_FINGER,

                    signalQuality = quality
                )

                return
            }
        }

        val waveform =
            signal.mapIndexed { index, point ->

                WavePoint(

                    x = index.toFloat(),

                    y = point.smoothed.toFloat()
                )
            }

        val peaks =
            peakDetector.detect(signal)

        val rrIntervals =
            rrIntervalCalculator.calculate(peaks)


        val hrv =
            hrvCalculator.calculate(rrIntervals)

        val bpm =
            heartRateCalculator.calculate(peaks)

        val elapsed =
            measurementTimer.elapsedSeconds()

        val state =
            if (elapsed < 2)
                MeasurementState.DETECTING
            else
                MeasurementState.MEASURING

        // Measurement finished
        if (measurementTimer.finished()) {

            audioPlayer.play(
                R.raw.measurement_complete
            )

            _uiState.value = _uiState.value.copy(

                measurementState =
                    MeasurementState.COMPLETE,

                finalBpm = bpm,

                bpm = null,

                waveform = waveform,

                signalQuality = quality
            )



            measurementTimer.reset()

            return
        }

        _uiState.value = _uiState.value.copy(

            measurementState = state,

            frameCount = _uiState.value.frameCount + 1,

            rawIntensity = processedSample.raw,

            filteredIntensity = processedSample.dcRemoved,

            normalizedIntensity = processedSample.normalized,

            sampleCount = signal.size,

            peakCount = peaks.size,

            bpm = bpm,

            waveform = waveform,

            signalQuality = quality,

            measurementProgress =
                measurementTimer.progress(),

            elapsedSeconds = elapsed,

            remainingSeconds =
                measurementTimer.remainingSeconds(),

            rrIntervals = rrIntervals,

            latestRR =
                rrIntervals.lastOrNull()?.intervalMillis,

            rmssd = hrv?.rmssd,

            sdnn = hrv?.sdnn,

            pnn50 = hrv?.pnn50,
        )
    }
}