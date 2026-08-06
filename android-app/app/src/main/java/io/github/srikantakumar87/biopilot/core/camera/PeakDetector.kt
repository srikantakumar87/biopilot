package io.github.srikantakumar87.biopilot.core.camera

import io.github.srikantakumar87.biopilot.core.camera.model.PPGSample
import io.github.srikantakumar87.biopilot.core.camera.model.Peak
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeakDetector @Inject constructor() {

    companion object {
        private const val MIN_PEAK_HEIGHT = 0.25
        private const val MIN_PEAK_DISTANCE_MS = 300L
    }

    fun detect(
        signal: List<PPGSample>
    ): List<Peak> {

        if (signal.size < 3)
            return emptyList()

        val peaks = mutableListOf<Peak>()

        var lastPeakTime = 0L

        for (i in 1 until signal.lastIndex) {

            val previous = signal[i - 1]
            val current = signal[i]
            val next = signal[i + 1]

            val isPeak =
                current.smoothed > previous.smoothed &&
                        current.smoothed > next.smoothed &&
                        current.smoothed > MIN_PEAK_HEIGHT

            if (!isPeak)
                continue

            if (current.timestamp - lastPeakTime < MIN_PEAK_DISTANCE_MS)
                continue

            peaks += Peak(
                timestamp = current.timestamp,
                value = current.smoothed
            )

            lastPeakTime = current.timestamp
        }

        return peaks
    }
}