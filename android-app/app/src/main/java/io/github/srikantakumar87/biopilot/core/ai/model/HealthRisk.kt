package io.github.srikantakumar87.biopilot.core.ai.model

enum class RiskLevel {
    LOW,
    MODERATE,
    HIGH
}

data class HealthRisk(

    val title: String,

    val description: String,

    val level: RiskLevel
)