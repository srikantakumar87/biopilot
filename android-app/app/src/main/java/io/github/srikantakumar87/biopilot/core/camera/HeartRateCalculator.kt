package io.github.srikantakumar87.biopilot.core.camera

import io.github.srikantakumar87.biopilot.core.camera.model.Peak
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class HeartRateCalculator @Inject constructor() {

    fun calculate(
        peaks: List<Peak>
    ): Int? {

        if (peaks.size < 2) {
            return null
        }

        val intervals = mutableListOf<Long>()

        for (i in 1 until peaks.size) {
            intervals += peaks[i].timestamp - peaks[i - 1].timestamp
        }

        val averageInterval = intervals.average()

        if (averageInterval <= 0.0) {
            return null
        }

        val bpm = 60_000.0 / averageInterval

        return bpm.toInt()
    }
}