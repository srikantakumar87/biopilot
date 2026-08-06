package io.github.srikantakumar87.biopilot.core.ai.model

data class HealthRecommendation(

    val title: String,

    val message: String,

    val priority: RecommendationPriority,

    val icon: RecommendationIcon
)