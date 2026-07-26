package io.github.srikantakumar87.biopilot.feature.home

data class HomeUiState(
    val userName: String = "Srikanta",
    val readinessScore: Int = 82,
    val steps: Int = 6842,
    val sleepHours: Double = 7.4,
    val heartRate: Int = 68,
    val weight: Double = 102.0
)