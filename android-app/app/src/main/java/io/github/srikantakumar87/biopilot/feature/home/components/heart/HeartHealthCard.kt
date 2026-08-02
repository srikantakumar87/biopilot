package io.github.srikantakumar87.biopilot.feature.home.components.heart

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
import androidx.compose.ui.unit.dp
import io.github.srikantakumar87.biopilot.core.designsystem.BioPilotCard

@Composable
fun HeartHealthCard(
    latestHeartRate: Long?,
    weeklyAverage: Double,
    restingEstimate: Double,
    modifier: Modifier = Modifier
) {

    val heartRate = latestHeartRate ?: 0L

    val progress =
        (heartRate / 180f)
            .coerceIn(0f, 1f)

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(900),
        label = "HeartProgress"
    )

    val (status, color) = heartStatus(heartRate)

    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = tween(
            durationMillis = 900,
            easing = FastOutSlowInEasing
        ),
        label = "HeartColor"
    )

    BioPilotCard(modifier = modifier) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HeartHeader()

            Spacer(Modifier.height(16.dp))

            HeartProgress(
                heartRate,
                animatedProgress,
                animatedColor
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = status,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = animatedColor
            )

            Spacer(Modifier.height(20.dp))

            HeartStats(
                weeklyAverage,
                restingEstimate
            )
        }
    }
}

@Composable
private fun HeartHeader() {

    Text(
        text = "❤️ Heart Health",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun HeartProgress(
    heartRate: Long,
    progress: Float,
    color: androidx.compose.ui.graphics.Color
) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            modifier = Modifier.size(140.dp),
            progress = { progress },
            color = color,
            strokeWidth = 12.dp
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = if (heartRate == 0L) "--" else "$heartRate",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )

            Text(
                text = "bpm",
                color = color
            )
        }
    }
}

@Composable
private fun HeartStats(
    weeklyAverage: Double,
    restingEstimate: Double
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        HeartStat(
            "Weekly Avg",
            "%.0f bpm".format(weeklyAverage)
        )

        HeartStat(
            "Resting",
            "%.0f bpm".format(restingEstimate)
        )
    }
}

@Composable
private fun HeartStat(
    title: String,
    value: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(title)

        Text(
            text = value,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun heartStatus(
    heartRate: Long
): Pair<String, androidx.compose.ui.graphics.Color> {

    return when {

        heartRate == 0L ->
            "No Data" to MaterialTheme.colorScheme.outline

        heartRate < 60 ->
            "Low" to MaterialTheme.colorScheme.secondary

        heartRate <= 100 ->
            "Normal" to MaterialTheme.colorScheme.primary

        else ->
            "High" to MaterialTheme.colorScheme.error
    }
}