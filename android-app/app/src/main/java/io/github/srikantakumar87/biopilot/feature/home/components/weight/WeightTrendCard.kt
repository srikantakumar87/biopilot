package io.github.srikantakumar87.biopilot.feature.home.components.weight

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.srikantakumar87.biopilot.core.designsystem.BioPilotCard
import io.github.srikantakumar87.biopilot.core.model.DailyWeight
import io.github.srikantakumar87.biopilot.feature.home.components.charts.WeightChart

@Composable
fun WeightTrendCard(
    weeklyWeights: List<DailyWeight>,
    modifier: Modifier = Modifier
) {

    if (weeklyWeights.isEmpty()) {

        BioPilotCard {
            Text("No weight history available.")
        }

        return
    }

    val currentWeight = weeklyWeights.last().weight

    val averageWeight = weeklyWeights
        .map { it.weight }
        .filter { it > 0 }
        .average()

    val previousWeight =
        weeklyWeights.dropLast(1).lastOrNull()?.weight ?: currentWeight

    val difference = currentWeight - previousWeight

    BioPilotCard(modifier = modifier) {

        Column {

            WeightHeader(difference)

            Spacer(Modifier.height(20.dp))

            WeightSummary(
                currentWeight = currentWeight,
                averageWeight = averageWeight
            )

            Spacer(Modifier.height(20.dp))

            WeightChart(
                weeklyWeights = weeklyWeights
            )
        }
    }
}

@Composable
private fun WeightHeader(
    difference: Double
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Outlined.MonitorWeight,
            contentDescription = null
        )

        Text(
            text = "Weight",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Icon(
            imageVector = Icons.Outlined.TrendingUp,
            contentDescription = null
        )

        Text(
            text = "%+.1f kg".format(difference),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun WeightSummary(
    currentWeight: Double,
    averageWeight: Double
) {

    Text(
        text = "%.1f kg".format(currentWeight),
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = "Current Weight",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(Modifier.height(16.dp))

    Text(
        text = "%.1f kg".format(averageWeight),
        style = MaterialTheme.typography.headlineSmall
    )

    Text(
        text = "Weekly Average",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}