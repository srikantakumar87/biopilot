package io.github.srikantakumar87.biopilot.core.ai

import io.github.srikantakumar87.biopilot.core.ai.model.HealthAnalysis
import io.github.srikantakumar87.biopilot.core.ai.model.HealthSnapshot
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthAnalyzer @Inject constructor() {

    fun analyze(
        snapshot: HealthSnapshot
    ): HealthAnalysis {

        val stepScore = calculateStepScore(snapshot)

        val sleepScore = calculateSleepScore(snapshot)

        val heartScore = calculateHeartScore(snapshot)

        val weightScore = calculateWeightScore(snapshot)

        val overallScore =
            (stepScore + sleepScore + heartScore + weightScore) / 4

        return HealthAnalysis(
            stepScore = stepScore,
            sleepScore = sleepScore,
            heartScore = heartScore,
            weightScore = weightScore,
            overallScore = overallScore
        )
    }

    private fun calculateStepScore(
        snapshot: HealthSnapshot
    ): Int {

        return ((snapshot.steps / 10_000.0) * 100)
            .toInt()
            .coerceIn(0, 100)
    }

    private fun calculateSleepScore(
        snapshot: HealthSnapshot
    ): Int {

        return ((snapshot.averageSleepHours / 8.0) * 100)
            .toInt()
            .coerceIn(0, 100)
    }

    private fun calculateHeartScore(
        snapshot: HealthSnapshot
    ): Int {

        val heartRate =
            snapshot.latestHeartRate ?: return 50

        return when (heartRate) {

            in 55..75 -> 100

            in 76..90 -> 85

            in 91..110 -> 65

            else -> 50
        }
    }

    private fun calculateWeightScore(
        snapshot: HealthSnapshot
    ): Int {

        return if (snapshot.weight == null)
            50
        else
            100
    }
}