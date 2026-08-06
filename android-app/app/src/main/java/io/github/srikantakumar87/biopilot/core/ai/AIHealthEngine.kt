package io.github.srikantakumar87.biopilot.core.ai

import io.github.srikantakumar87.biopilot.core.ai.model.AIReport
import io.github.srikantakumar87.biopilot.core.ai.model.HealthSnapshot
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIHealthEngine @Inject constructor(
    private val analyzer: HealthAnalyzer,

    private val insightGenerator: InsightGenerator,

    private val recommendationEngine: RecommendationEngine
) {

    fun generateReport(
        snapshot: HealthSnapshot
    ): AIReport {

        val analysis = analyzer.analyze(snapshot)

        val insights = insightGenerator.generate(
            snapshot = snapshot,
            analysis = analysis
        )
        val recommendations = recommendationEngine.generate(
            snapshot,
            analysis
        )

        return AIReport(
            snapshot = snapshot,
            analysis = analysis,
            insights = insights,
            recommendations = recommendations
        )
    }
}