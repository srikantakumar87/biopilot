package io.github.srikantakumar87.biopilot.feature.camera.heartrate

import io.github.srikantakumar87.biopilot.core.camera.model.SignalQuality
import io.github.srikantakumar87.biopilot.core.camera.model.WavePoint
import io.github.srikantakumar87.biopilot.core.camera.model.MeasurementState
import io.github.srikantakumar87.biopilot.core.camera.model.RRInterval

data class CameraHeartRateUiState(

    val cameraRunning: Boolean = false,

    val frameCount: Long = 0,

    val rawIntensity: Double = 0.0,
    val filteredIntensity: Double = 0.0,

    val sampleCount: Int = 0,

    val peakCount: Int = 0,

    val bpm: Int? = null,
    val measurementState: MeasurementState =  MeasurementState.WAITING_FOR_FINGER,
    val waveform: List<WavePoint> = emptyList(),
    val normalizedIntensity: Double = 0.0,
    val signalQuality: SignalQuality =
        SignalQuality.NO_SIGNAL,
    val measurementProgress: Float = 0f,

    val elapsedSeconds: Int = 0,

    val remainingSeconds: Int = 30,

    val finalBpm: Int? = null,

    val red: Double = 0.0,
    val green: Double = 0.0,
    val blue: Double = 0.0,
    val processingProgress: Float = 0f,
    val rrIntervals: List<RRInterval> = emptyList(),

    val latestRR: Long? = null,
    val rmssd: Double? = null,

    val sdnn: Double? = null,

    val pnn50: Double? = null,

    )