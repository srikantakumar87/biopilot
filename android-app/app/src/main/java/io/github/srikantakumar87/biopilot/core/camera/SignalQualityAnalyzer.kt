package io.github.srikantakumar87.biopilot.core.camera

import io.github.srikantakumar87.biopilot.core.camera.model.PPGSample
import io.github.srikantakumar87.biopilot.core.camera.model.SignalQuality
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

@Singleton
class SignalQualityAnalyzer @Inject constructor() {

    fun analyze(
        signal: List<PPGSample>
    ): SignalQuality {

        if (signal.size < 60)
            return SignalQuality.NO_SIGNAL

        val values = signal.map {
            it.smoothed
        }

        val amplitude =
            values.max() - values.min()

        return when {

            amplitude < 0.05 ->
                SignalQuality.NO_SIGNAL

            amplitude < 0.10 ->
                SignalQuality.POOR

            amplitude < 0.20 ->
                SignalQuality.FAIR

            amplitude < 0.35 ->
                SignalQuality.GOOD

            else ->
                SignalQuality.EXCELLENT
        }
    }
}