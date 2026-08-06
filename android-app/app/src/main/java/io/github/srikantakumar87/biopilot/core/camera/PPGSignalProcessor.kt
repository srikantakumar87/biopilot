package io.github.srikantakumar87.biopilot.core.camera

import io.github.srikantakumar87.biopilot.core.camera.model.PPGSample
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PPGSignalProcessor @Inject constructor() {

    private val samples = mutableListOf<PPGSample>()

    private val averageWindow = 30

    private var previousEma = 0.0

    private companion object {
        const val EMA_ALPHA = 0.20
    }

    fun addSample(sample: PPGSample): PPGSample {

        val average = calculateMovingAverage()

        val dcRemoved = sample.raw - average

        val normalized = normalize(dcRemoved)

        val smoothed = smooth(normalized)



        val processedSample = sample.copy(
            dcRemoved = dcRemoved,
            normalized = normalized,
            smoothed = smoothed
        )

        samples += processedSample

        val cutoff = System.currentTimeMillis() - 10_000

        samples.removeAll {
            it.timestamp < cutoff
        }

        return processedSample
    }

    private fun calculateMovingAverage(): Double {

        if (samples.isEmpty())
            return 0.0

        return samples
            .takeLast(averageWindow)
            .map { it.raw }
            .average()
    }

    private fun normalize(
        value: Double
    ): Double {

        val window = samples.takeLast(averageWindow)

        if (window.isEmpty())
            return 0.0

        val max = window.maxOf { it.dcRemoved }

        val min = window.minOf { it.dcRemoved }

        val range = max - min

        if (range < 0.0001)
            return 0.0

        return ((value - min) / range) * 2.0 - 1.0
    }

    private fun smooth(
        value: Double
    ): Double {

        previousEma =
            EMA_ALPHA * value +
                    (1.0 - EMA_ALPHA) * previousEma

        return previousEma
    }

    fun getSignal(): List<PPGSample> =
        samples

    fun clear() {

        samples.clear()

        previousEma = 0.0
    }
}