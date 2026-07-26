package io.github.srikantakumar87.biopilot.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.SmartToy
import io.github.srikantakumar87.biopilot.core.model.HealthMetric
import io.github.srikantakumar87.biopilot.core.model.QuickAction

data class HomeUiState(
    val userName: String = "Srikanta",
    val readinessScore: Int = 82,
    val quickActions: List<QuickAction> = listOf(
        QuickAction("Connect", Icons.Outlined.Favorite),
        QuickAction("Trends", Icons.Outlined.ShowChart),
        QuickAction("AI Coach", Icons.Outlined.SmartToy),
        QuickAction("Settings", Icons.Outlined.Settings)
    ),

    val metrics: List<HealthMetric> = listOf(
        HealthMetric(
            title = "Steps",
            value = "6,842",
            unit = "steps",
            icon = Icons.Outlined.DirectionsWalk
        ),
        HealthMetric(
            title = "Sleep",
            value = "7h 24m",
            unit = "",
            icon = Icons.Outlined.Bedtime
        ),
        HealthMetric(
            title = "Heart Rate",
            value = "68",
            unit = "bpm",
            icon = Icons.Outlined.Favorite
        ),
        HealthMetric(
            title = "Weight",
            value = "102",
            unit = "kg",
            icon = Icons.Outlined.MonitorWeight
        )
    )
)