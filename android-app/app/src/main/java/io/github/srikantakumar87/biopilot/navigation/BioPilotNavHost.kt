package io.github.srikantakumar87.biopilot.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.srikantakumar87.biopilot.feature.history.HistoryScreen
import io.github.srikantakumar87.biopilot.feature.home.HomeScreen
import io.github.srikantakumar87.biopilot.feature.insights.InsightsScreen
import io.github.srikantakumar87.biopilot.feature.readiness.ReadinessScreen
import io.github.srikantakumar87.biopilot.feature.settings.SettingsScreen

@Composable
fun BioPilotNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route,
        modifier = modifier
    ) {
        composable(Destination.Home.route) {
            HomeScreen()
        }

        composable(Destination.Readiness.route) {
            ReadinessScreen()
        }

        composable(Destination.Insights.route) {
            InsightsScreen()
        }

        composable(Destination.History.route) {
            HistoryScreen()
        }

        composable(Destination.Settings.route) {
            SettingsScreen()
        }
    }
}