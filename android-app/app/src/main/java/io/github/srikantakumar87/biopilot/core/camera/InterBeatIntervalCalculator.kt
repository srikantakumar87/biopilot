package io.github.srikantakumar87.biopilot.core.camera

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterBeatIntervalCalculator @Inject constructor() {

    fun calculate(
        beatTimestamps: List<Long>
    ): List<Long> {

        if (beatTimestamps.size < 2)
            return emptyList()

        return beatTimestamps
            .zipWithNext()
            .map {

                it.second - it.first
            }
    }
}