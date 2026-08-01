package io.github.srikantakumar87.biopilot.feature.home.components.activity

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun StepGoalCard(
    steps: Long,
    goal: Long,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val remaining = (goal - steps).coerceAtLeast(0)

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(900),
        label = "StepGoalProgress"
    )

    val percentage = (animatedProgress * 100).toInt()

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            StepGoalHeader()

            StepGoalProgress(
                percentage = percentage,
                steps = steps,
                progress = animatedProgress
            )

            StepGoalStats(
                goal = goal,
                remaining = remaining
            )

            StepGoalMessage(
                steps = steps,
                goal = goal,
                remaining = remaining
            )
        }
    }
}

@Composable
private fun StepGoalHeader() {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Outlined.DirectionsWalk,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Today's Activity",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun StepGoalProgress(
    percentage: Int,
    steps: Long,
    progress: Float
) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(180.dp),
            strokeWidth = 12.dp
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.displaySmall
            )

            Text(
                text = "%,d".format(steps),
                style = MaterialTheme.typography.titleMedium
            )

            Text("steps")
        }
    }
}

@Composable
private fun StepGoalStats(
    goal: Long,
    remaining: Long
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        StepStat(
            title = "Goal",
            value = goal
        )

        StepStat(
            title = "Remaining",
            value = remaining
        )
    }
}

@Composable
private fun StepStat(
    title: String,
    value: Long
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(title)

        Text(
            text = "%,d".format(value),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun StepGoalMessage(
    steps: Long,
    goal: Long,
    remaining: Long
) {

    val message = when {
        steps >= goal ->
            "🎉 Goal achieved! Great job!"

        remaining <= 1_000 ->
            "🔥 Only %,d steps to go!".format(remaining)

        else ->
            "🚶 %,d steps remaining".format(remaining)
    }

    Text(
        text = message,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge
    )

    Spacer(modifier = Modifier.height(4.dp))
}