package io.github.srikantakumar87.biopilot.feature.home.components.charts

import androidx.compose.runtime.Composable
import io.github.srikantakumar87.biopilot.core.model.DailyWeight

@Composable
fun WeightChart(
    weeklyWeights: List<DailyWeight>
) {
    LineChart(
        values = weeklyWeights.map {
            it.weight.toFloat()
        }
    )
}