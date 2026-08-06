package io.github.srikantakumar87.biopilot.core.camera

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StabilizationTimer @Inject constructor() {

    companion object {
        const val DURATION_SECONDS = 2
        const val DURATION_MS = DURATION_SECONDS * 1000L
    }

    private var startTime = 0L

    fun start() {
        if (startTime == 0L) {
            startTime = System.currentTimeMillis()
        }
    }

    fun reset() {
        startTime = 0L
    }

    fun isRunning(): Boolean =
        startTime != 0L

    fun elapsedMillis(): Long {

        if (!isRunning())
            return 0L

        return System.currentTimeMillis() - startTime
    }

    fun elapsedSeconds(): Int =
        (elapsedMillis() / 1000L).toInt()

    fun remainingMillis(): Long =
        (DURATION_MS - elapsedMillis())
            .coerceAtLeast(0L)

    fun progress(): Float =
        (elapsedMillis().toFloat() / DURATION_MS)
            .coerceIn(0f, 1f)

    fun finished(): Boolean =
        elapsedMillis() >= DURATION_MS
}