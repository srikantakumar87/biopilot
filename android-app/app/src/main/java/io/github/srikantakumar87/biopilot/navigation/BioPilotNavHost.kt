package io.github.srikantakumar87.biopilot.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.srikantakumar87.biopilot.feature.home.HomeScreen

@Composable
fun BioPilotNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen()
        }
    }
}