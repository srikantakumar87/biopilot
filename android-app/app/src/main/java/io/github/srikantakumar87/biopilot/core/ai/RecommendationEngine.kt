package io.github.srikantakumar87.biopilot.core.ai

import io.github.srikantakumar87.biopilot.core.ai.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommendationEngine @Inject constructor() {

    fun generate(
        snapshot: HealthSnapshot,
        analysis: HealthAnalysis
    ): List<HealthRecommendation> {

        val recommendations = mutableListOf<HealthRecommendation>()

        addWalkingRecommendation(
            recommendations,
            snapshot
        )

        addSleepRecommendation(
            recommendations,
            snapshot
        )

        addHeartRecommendation(
            recommendations,
            snapshot
        )

        addWeightRecommendation(
            recommendations,
            snapshot
        )

        return recommendations
    }

    private fun addWalkingRecommendation(
        recommendations: MutableList<HealthRecommendation>,
        snapshot: HealthSnapshot
    ) {

        if (snapshot.steps >= 10_000) {

            recommendations += HealthRecommendation(
                title = "Great Activity",
                message = "You reached today's step goal. Keep it up!",
                priority = RecommendationPriority.LOW,
                icon = RecommendationIcon.WALK
            )

        } else {

            val remaining = 10_000 - snapshot.steps

            recommendations += HealthRecommendation(
                title = "Walk More",
                message = "Walk another $remaining steps today to reach your goal.",
                priority = RecommendationPriority.HIGH,
                icon = RecommendationIcon.WALK
            )
        }
    }

    private fun addSleepRecommendation(
        recommendations: MutableList<HealthRecommendation>,
        snapshot: HealthSnapshot
    ) {
        // We'll implement next
    }

    private fun addHeartRecommendation(
        recommendations: MutableList<HealthRecommendation>,
        snapshot: HealthSnapshot
    ) {
        // We'll implement next
    }

    private fun addWeightRecommendation(
        recommendations: MutableList<HealthRecommendation>,
        snapshot: HealthSnapshot
    ) {
        // We'll implement next
    }
}

