package io.github.srikantakumar87.biopilot.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun BioPilotApp() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->

        BioPilotNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}