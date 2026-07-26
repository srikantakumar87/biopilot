package io.github.srikantakumar87.biopilot.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.srikantakumar87.biopilot.core.designsystem.MetricCard
import io.github.srikantakumar87.biopilot.core.model.HealthMetric

@Composable
fun MetricGrid(
    metrics: List<HealthMetric>,
    modifier: Modifier = Modifier
) {
    if (metrics.size < 4) return

    Column(modifier = modifier.fillMaxWidth()) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCard(
                title = metrics[0].title,
                value = metrics[0].value,
                unit = metrics[0].unit,
                icon = metrics[0].icon,
                modifier = Modifier.weight(1f)
            )

            MetricCard(
                title = metrics[1].title,
                value = metrics[1].value,
                unit = metrics[1].unit,
                icon = metrics[1].icon,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCard(
                title = metrics[2].title,
                value = metrics[2].value,
                unit = metrics[2].unit,
                icon = metrics[2].icon,
                modifier = Modifier.weight(1f)
            )

            MetricCard(
                title = metrics[3].title,
                value = metrics[3].value,
                unit = metrics[3].unit,
                icon = metrics[3].icon,
                modifier = Modifier.weight(1f)
            )
        }
    }
}