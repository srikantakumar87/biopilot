package io.github.srikantakumar87.biopilot.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.SmartToy
import io.github.srikantakumar87.biopilot.core.model.QuickAction
import io.github.srikantakumar87.biopilot.core.model.DailySteps

data class HomeUiState(
    val userName: String = "Srikanta",
    val readinessScore: Int = 82,

    val steps: Long = 0,
    val stepGoal: Long = 10_000,
    val sleep: String = "7h 24m",
    val heartRate: Long? = null,
    val weight: Double? = null,
    val weeklySteps: List<DailySteps> = emptyList(),


    val quickActions: List<QuickAction> = listOf(
        QuickAction("Connect", Icons.Outlined.Favorite),
        QuickAction("Trends", Icons.Outlined.ShowChart),
        QuickAction("AI Coach", Icons.Outlined.SmartToy),
        QuickAction("Settings", Icons.Outlined.Settings)
    )

){
    val stepProgress: Float
        get() = (steps.toFloat() / stepGoal).coerceIn(0f, 1f)
}