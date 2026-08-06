package io.github.srikantakumar87.biopilot.feature.home.components.weight

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.srikantakumar87.biopilot.core.designsystem.BioPilotCard
import io.github.srikantakumar87.biopilot.core.model.BodyComposition

@Composable
fun BodyCompositionCard(
    bodyComposition: BodyComposition,
    modifier: Modifier = Modifier
) {

    BioPilotCard(modifier = modifier) {

        Column {

            BodyCompositionHeader()

            Spacer(Modifier.height(20.dp))

            CompositionRow(
                label = "Body Fat",
                value = bodyComposition.bodyFatPercent?.let {
                    "%.1f %%".format(it)
                } ?: "--"
            )

            Spacer(Modifier.height(12.dp))

            CompositionRow(
                label = "Lean Mass",
                value = bodyComposition.leanBodyMassKg?.let {
                    "%.1f kg".format(it)
                } ?: "--"
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = compositionMessage(bodyComposition),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun BodyCompositionHeader() {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Outlined.MonitorHeart,
            contentDescription = null
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = "Body Composition",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun CompositionRow(
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

private fun compositionMessage(
    bodyComposition: BodyComposition
): String {

    return if (
        bodyComposition.bodyFatPercent == null &&
        bodyComposition.leanBodyMassKg == null
    ) {
        "No body composition data available."
    } else {
        "Body composition measurements are available."
    }
}