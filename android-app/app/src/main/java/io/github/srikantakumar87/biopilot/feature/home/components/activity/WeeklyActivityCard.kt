package io.github.srikantakumar87.biopilot.feature.home.components.activity

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.srikantakumar87.biopilot.core.designsystem.BioPilotCard
import io.github.srikantakumar87.biopilot.core.model.DailySteps

@Composable
fun WeeklyActivityCard(
    weeklySteps: List<DailySteps>,
    modifier: Modifier = Modifier
) {
    BioPilotCard(modifier = modifier) {

        Column {

            WeeklyActivityHeader()

            Spacer(modifier = Modifier.height(16.dp))

            if (weeklySteps.isEmpty()) {

                EmptyWeeklyActivity()

                return@BioPilotCard
            }

            val maxSteps = weeklySteps.maxOf { it.steps }

            weeklySteps.forEach { day ->

                WeeklyActivityRow(
                    day = day.dayLabel,
                    steps = day.steps,
                    progress = if (maxSteps == 0L) {
                        0f
                    } else {
                        day.steps.toFloat() / maxSteps
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun WeeklyActivityHeader() {

    Text(
        text = "Weekly Activity",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun EmptyWeeklyActivity() {

    Text(
        text = "No weekly activity available.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun WeeklyActivityRow(
    day: String,
    steps: Long,
    progress: Float
) {

    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(800),
        label = "WeeklyActivityBar"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = day,
            modifier = Modifier.width(42.dp),
            style = MaterialTheme.typography.bodyMedium
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(10.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(50)
                )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .fillMaxHeight()
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50)
                    )
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "%,d".format(steps),
            modifier = Modifier.width(72.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}