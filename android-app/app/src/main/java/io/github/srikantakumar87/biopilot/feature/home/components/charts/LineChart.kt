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

@Composable
fun LineChart(
    values: List<Float>,
    modifier: Modifier = Modifier
) {

    if (values.isEmpty()) {

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.Center
        ) {

            Text("No data available.")

        }

        return
    }

    val modelProducer = remember {
        CartesianChartModelProducer()
    }

    LaunchedEffect(values) {

        modelProducer.runTransaction {

            lineSeries {
                series(values)
            }
        }
    }

    CartesianChartHost(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        chart = rememberCartesianChart(
            rememberLineCartesianLayer()
        ),
        modelProducer = modelProducer
    )
}