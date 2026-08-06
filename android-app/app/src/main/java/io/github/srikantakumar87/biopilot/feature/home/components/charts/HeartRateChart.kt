package io.github.srikantakumar87.biopilot.feature.home.components.charts

import androidx.compose.runtime.Composable
import io.github.srikantakumar87.biopilot.core.model.DailyHeartRate

@Composable
fun HeartRateChart(
    weeklyHeartRates: List<DailyHeartRate>
) {
    LineChart(
        values = weeklyHeartRates.map {
            it.heartRate.toFloat()
        }

    )
}