package io.github.srikantakumar87.biopilot.feature.home.components.charts

import androidx.compose.runtime.Composable
import io.github.srikantakumar87.biopilot.core.model.DailySteps

@Composable
fun WeeklyStepsChart(
    weeklySteps: List<DailySteps>
) {
    LineChart(
        values = weeklySteps.map {
            it.steps.toFloat()
        }
    )
}