package io.github.srikantakumar87.biopilot.feature.home.components.weight

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.srikantakumar87.biopilot.core.designsystem.BioPilotCard

@Composable
fun BMIStatusCard(
    weight: Double?,
    heightCm: Double,
    modifier: Modifier = Modifier
) {

    val bmi = remember(weight, heightCm) {

        if (weight == null || heightCm <= 0) {

            null

        } else {

            weight / ((heightCm / 100.0) * (heightCm / 100.0))
        }
    }

    BioPilotCard(modifier = modifier) {

        if (bmi == null) {

            Text("BMI unavailable")

            return@BioPilotCard
        }

        Column {

            BMIHeader()

            Spacer(Modifier.height(20.dp))

            BMIValue(bmi)

            Spacer(Modifier.height(24.dp))

            BMIScale(
                bmi = bmi,
                heightCm = heightCm
            )
        }
    }
}
@Composable
private fun BMIHeader() {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Outlined.MonitorWeight,
            contentDescription = null
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = "BMI Status",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
private fun BMIValue(
    bmi: Double
) {

    Text(
        text = "%.1f".format(bmi),
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold
    )

    val categoryColor = when {
        bmi < 18.5 -> MaterialTheme.colorScheme.secondary
        bmi < 25 -> MaterialTheme.colorScheme.primary
        bmi < 30 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }

    Text(
        text = bmiCategory(bmi),
        color = categoryColor,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold
    )
}
@Composable
private fun BMIScale(
    bmi: Double,
    heightCm: Double
) {

    val progress =
        ((bmi - 15) / 25)
            .coerceIn(0.0, 1.0)
            .toFloat()

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(800),
        label = "BMIProgress"
    )

    Spacer(Modifier.height(16.dp))

    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(Modifier.height(8.dp))

    Text(
        text = "Healthy: 18.5 – 24.9",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(Modifier.height(12.dp))

    Text(
        text = "Height: %.0f cm".format(heightCm),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(Modifier.height(12.dp))

    Text(
        text = bmiAdvice(bmi),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

private fun bmiAdvice(bmi: Double): String =
    when {
        bmi < 18.5 ->
            "Consider gradually gaining weight through a balanced diet."

        bmi < 25 ->
            "Your BMI is within the healthy range. Keep it up!"

        bmi < 30 ->
            "A modest weight reduction may improve long-term health."

        else ->
            "Consider discussing a structured weight management plan with a healthcare professional."
    }
private fun bmiCategory(
    bmi: Double
): String {

    return when {

        bmi < 18.5 ->
            "Underweight"

        bmi < 25 ->
            "Healthy Weight"

        bmi < 30 ->
            "Overweight"

        else ->
            "Obese"
    }
}