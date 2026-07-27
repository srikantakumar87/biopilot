package io.github.srikantakumar87.biopilot.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import java.util.Calendar

@Composable
fun GreetingCard(
    userName: String
) {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    val greeting = when (hour) {
        in 5..11 -> "Good Morning 👋"
        in 12..16 -> "Good Afternoon ☀️"
        in 17..20 -> "Good Evening 🌇"
        else -> "Good Night 🌙"
    }

    val subtitle = when (hour) {
        in 5..11 -> "Let's start your day strong 💪"
        in 12..16 -> "Keep your momentum going 🚶"
        in 17..20 -> "Time to wind down and recover 🌿"
        else -> "Rest well and recharge 😴"
    }
    Column {
        Text(
            text = greeting,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = userName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Stay healthy today 💚",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}