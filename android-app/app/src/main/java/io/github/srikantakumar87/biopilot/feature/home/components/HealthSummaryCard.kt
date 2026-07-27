package io.github.srikantakumar87.biopilot.feature.home.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.srikantakumar87.biopilot.core.designsystem.BioPilotCard
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.ui.text.style.TextAlign




@Composable
fun HealthSummaryCard(
    score: Int,
    message: String
) {
    val animatedScore by animateIntAsState(
        targetValue = score,
        animationSpec = tween(
            durationMillis = 1200
        ),
        label = "RecoveryScore"
    )


    val animatedProgress by animateFloatAsState(
        targetValue = score / 100f,
        animationSpec = tween(
            durationMillis = 1200
        ),
        label = "RecoveryProgress"
    )

    val status = when {
        score >= 90 -> "Excellent"
        score >= 75 -> "Good"
        score >= 60 -> "Fair"
        else -> "Needs Attention"
    }

    val color = when {
        score >= 90 -> MaterialTheme.colorScheme.tertiary
        score >= 75 -> MaterialTheme.colorScheme.primary
        score >= 60 -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.error
    }
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = tween(
            durationMillis = 1200,
            easing = FastOutSlowInEasing
        ),
        label = "RecoveryColor"
    )

    BioPilotCard {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Recovery Score",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            CircularProgressIndicator(
                modifier = Modifier.size(80.dp),
                progress = { animatedProgress },
                color = animatedColor,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                strokeWidth = 10.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$animatedScore%",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontFeatureSettings = "tnum"
                ),
                fontWeight = FontWeight.ExtraBold,
                color = animatedColor
            )

            Text(
                text = status,
                style = MaterialTheme.typography.titleMedium,
                color = animatedColor,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}