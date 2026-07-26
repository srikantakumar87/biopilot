package io.github.srikantakumar87.biopilot.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GreetingCard(
    userName: String
) {
    Column {
        Text(
            text = "Good Morning 👋",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = userName,
            style = MaterialTheme.typography.titleMedium
        )
    }
}