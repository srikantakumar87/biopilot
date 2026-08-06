package io.github.srikantakumar87.biopilot.feature.home.components.heart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.srikantakumar87.biopilot.core.designsystem.BioPilotCard
import io.github.srikantakumar87.biopilot.core.model.DailyHeartRate
import io.github.srikantakumar87.biopilot.feature.home.components.charts.HeartRateChart
import io.github.srikantakumar87.biopilot.feature.home.components.charts.LineChart

@Composable
fun HeartTrendCard(
    weeklyHeartRates: List<DailyHeartRate>,
    modifier: Modifier = Modifier
) {

    BioPilotCard(modifier = modifier) {

        Column {

            HeartTrendHeader()

            Spacer(Modifier.height(20.dp))

            if (weeklyHeartRates.isEmpty()) {

                Text(
                    text = "No heart rate history available.",
                    style = MaterialTheme.typography.bodyMedium
                )

                return@BioPilotCard
            }

            val validHeartRates = weeklyHeartRates
                .map { it.heartRate }
                .filter { it > 0 }

            val average =
                if (validHeartRates.isEmpty()) {
                    0.0
                } else {
                    validHeartRates.average()
                }

            Text(
                text = "Average: %.0f bpm".format(average),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(20.dp))

            HeartRateChart(
                weeklyHeartRates = weeklyHeartRates
            )
        }
    }
}

@Composable
private fun HeartTrendHeader() {

    Text(
        text = "❤️ Heart Trend",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

