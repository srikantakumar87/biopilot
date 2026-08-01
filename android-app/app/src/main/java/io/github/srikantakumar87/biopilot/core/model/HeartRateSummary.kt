package io.github.srikantakumar87.biopilot.core.model

data class HeartRateSummary(
    val latest: Long?,
    val weeklyAverage: Double,
    val restingEstimate: Double
)