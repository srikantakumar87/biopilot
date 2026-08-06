package io.github.srikantakumar87.biopilot.core.ai.model

data class HealthInsight(

    val title: String,

    val message: String,

    val severity: InsightSeverity,

    val metric: String? = null,

    val icon: InsightIcon? = null
)