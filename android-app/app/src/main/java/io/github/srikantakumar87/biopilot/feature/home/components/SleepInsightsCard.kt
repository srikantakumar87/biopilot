package io.github.srikantakumar87.biopilot.feature.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.srikantakumar87.biopilot.core.designsystem.BioPilotCard

@Composable
fun SleepInsightsCard(
    averageSleepHours: Double,
    sleepGoalHours: Double = 8.0,
    modifier: Modifier = Modifier
) {

    val progress =
        (averageSleepHours / sleepGoalHours)
            .coerceIn(0.0, 1.0)
            .toFloat()

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(900),
        label = "SleepProgress"
    )

    val color = when {
        averageSleepHours >= 8 -> MaterialTheme.colorScheme.tertiary
        averageSleepHours >= 7 -> MaterialTheme.colorScheme.primary
        averageSleepHours >= 6 -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.error
    }

    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = tween(
            durationMillis = 900,
            easing = FastOutSlowInEasing
        ),
        label = "SleepColor"
    )

    val hours = averageSleepHours.toInt()
    val minutes = ((averageSleepHours - hours) * 60).toInt()

    BioPilotCard(modifier = modifier) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Sleep Insights",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator(
                    modifier = Modifier.size(140.dp),
                    progress = { animatedProgress },
                    color = animatedColor,
                    strokeWidth = 12.dp
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "${hours}h ${minutes}m",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = animatedColor
                    )

                    Text(
                        text = "Average",
                        style = MaterialTheme.typography.bodyMedium,
                        color = animatedColor
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            SleepMetricRow("Goal", "${sleepGoalHours.toInt()}h")

            SleepMetricRow(
                "Progress",
                "${(progress * 100).toInt()}%"
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = sleepMessage(averageSleepHours),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
private fun SleepMetricRow(
    label: String,
    value: String
) {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = label,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun sleepMessage(hours: Double): String =
    when {
        hours >= 8 -> "Excellent sleep consistency."
        hours >= 7 -> "You're sleeping consistently."
        hours >= 6 -> "Try getting a little more sleep."
        else -> "Your sleep duration is below the recommended range."
    }