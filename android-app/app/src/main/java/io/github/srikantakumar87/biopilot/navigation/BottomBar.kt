package io.github.srikantakumar87.biopilot.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val destinations = listOf(
        Destination.Home,
        Destination.Readiness,
        Destination.Insights,
        Destination.History,
        Destination.Settings
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    NavigationBar {
        destinations.forEach { destination ->

            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.route == destination.route
                } == true,

                onClick = {
                    navController.navigate(destination.route) {
                        launchSingleTop = true
                        restoreState = true

                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },

                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.title
                    )
                },

                label = {
                    Text(destination.title)
                }
            )
        }
    }
}