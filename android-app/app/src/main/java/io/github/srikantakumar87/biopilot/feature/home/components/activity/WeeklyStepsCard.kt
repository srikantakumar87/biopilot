package io.github.srikantakumar87.biopilot.feature.home.components.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
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
import io.github.srikantakumar87.biopilot.core.model.DailySteps
import io.github.srikantakumar87.biopilot.feature.home.components.charts.WeeklyStepsChart

@Composable
fun WeeklyStepsCard(
    weeklySteps: List<DailySteps>,
    modifier: Modifier = Modifier
) {

    if (weeklySteps.isEmpty()) {

        BioPilotCard {

            Text("No weekly activity available.")

        }

        return
    }

    val totalSteps = weeklySteps.sumOf { it.steps }

    val averageSteps = totalSteps / weeklySteps.size

    val yesterday = weeklySteps.dropLast(1).lastOrNull()?.steps ?: 0

    val today = weeklySteps.last().steps

    val trend =
        if (yesterday == 0L) 0
        else (((today - yesterday).toFloat() / yesterday) * 100).toInt()

    BioPilotCard(modifier = modifier) {

        Column {

            WeeklyStepsHeader(trend)

            Spacer(Modifier.height(20.dp))

            WeeklyStepsSummary(
                totalSteps,
                averageSteps
            )

            Spacer(Modifier.height(20.dp))

            WeeklyStepsChart(
                weeklySteps = weeklySteps
            )
        }
    }
}

@Composable
private fun WeeklyStepsHeader(
    trend: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Weekly Steps",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Outlined.TrendingUp,
            contentDescription = null
        )

        Text(
            text = "$trend%",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun WeeklyStepsSummary(
    totalSteps: Long,
    averageSteps: Long
) {

    Text(
        text = "%,d".format(totalSteps),
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = "Last 7 days",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(Modifier.height(16.dp))

    Text(
        text = "%,d/day".format(averageSteps),
        style = MaterialTheme.typography.headlineSmall
    )

    Text(
        text = "Daily average",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}