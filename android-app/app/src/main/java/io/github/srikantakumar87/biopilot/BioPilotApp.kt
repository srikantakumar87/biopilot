package io.github.srikantakumar87.biopilot

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.srikantakumar87.biopilot.navigation.BioPilotNavHost

@Composable
fun BioPilotApp() {
    Scaffold { innerPadding ->
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            BioPilotNavHost()
        }
    }
}