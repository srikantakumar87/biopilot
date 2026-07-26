package io.github.srikantakumar87.biopilot.feature.home.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import io.github.srikantakumar87.biopilot.core.designsystem.BioPilotCard

@Composable
fun HealthSummaryCard(
    score: Int,
    message: String
) {
    BioPilotCard {

        Text(
            text = "Readiness",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "$score",
            style = MaterialTheme.typography.displayLarge
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}