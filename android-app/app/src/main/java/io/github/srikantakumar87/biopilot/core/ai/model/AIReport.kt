package io.github.srikantakumar87.biopilot.core.ai.model

data class AIReport(

    val snapshot: HealthSnapshot,

    val analysis: HealthAnalysis,

    val insights: List<HealthInsight>,

    val recommendations: List<HealthRecommendation>
)