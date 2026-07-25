package io.github.srikantakumar87.biopilot

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.srikantakumar87.biopilot.ui.theme.BiopilotTheme

@Composable
fun BioPilotApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("BioPilot")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BioPilotAppPreview() {
    BiopilotTheme {
        BioPilotApp()
    }
}