package io.github.srikantakumar87.biopilot.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.srikantakumar87.biopilot.core.designsystem.QuickActionCard
import io.github.srikantakumar87.biopilot.core.model.QuickAction

@Composable
fun QuickActionsRow(
    actions: List<QuickAction>,
    modifier: Modifier = Modifier
) {
    if (actions.size < 4) return

    Column(modifier = modifier.fillMaxWidth()) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionCard(
                title = actions[0].title,
                icon = actions[0].icon,
                onClick = actions[0].onClick,
                modifier = Modifier.weight(1f)
            )

            QuickActionCard(
                title = actions[1].title,
                icon = actions[1].icon,
                onClick = actions[1].onClick,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionCard(
                title = actions[2].title,
                icon = actions[2].icon,
                onClick = actions[2].onClick,
                modifier = Modifier.weight(1f)
            )

            QuickActionCard(
                title = actions[3].title,
                icon = actions[3].icon,
                onClick = actions[3].onClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}