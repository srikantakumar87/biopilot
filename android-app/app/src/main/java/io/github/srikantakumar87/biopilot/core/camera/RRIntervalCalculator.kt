package io.github.srikantakumar87.biopilot.core.camera

import io.github.srikantakumar87.biopilot.core.camera.model.Peak
import io.github.srikantakumar87.biopilot.core.camera.model.RRInterval
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RRIntervalCalculator @Inject constructor() {

    fun calculate(
        peaks: List<Peak>
    ): List<RRInterval> {

        if (peaks.size < 2)
            return emptyList()

        val intervals = mutableListOf<RRInterval>()

        for (i in 1 until peaks.size) {

            val previous = peaks[i - 1]

            val current = peaks[i]

            intervals += RRInterval(

                previousPeak = previous.timestamp,

                currentPeak = current.timestamp,

                intervalMillis =
                    current.timestamp - previous.timestamp
            )
        }

        return intervals
    }
}