package io.github.srikantakumar87.biopilot.feature.home.components.charts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import io.github.srikantakumar87.biopilot.core.model.DailySteps

class LineChart {
}

@Composable
fun WeeklyStepsChart(
    weeklySteps: List<DailySteps>
) {

    if (weeklySteps.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No weekly activity available.")
        }
        return
    }

    val modelProducer = remember {
        CartesianChartModelProducer()
    }

    LaunchedEffect(weeklySteps) {
        modelProducer.runTransaction {
            lineSeries {
                series(
                    weeklySteps.map { it.steps.toFloat() }
                )
            }
        }
    }

    CartesianChartHost(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        chart = rememberCartesianChart(
            rememberLineCartesianLayer()
        ),
        modelProducer = modelProducer
    )
}