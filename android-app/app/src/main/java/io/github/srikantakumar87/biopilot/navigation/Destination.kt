package io.github.srikantakumar87.biopilot.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : Destination(
        "home",
        "Home",
        Icons.Default.Home
    )

    data object Readiness : Destination(
        "readiness",
        "Readiness",
        Icons.Default.Favorite
    )

    data object Insights : Destination(
        "insights",
        "Insights",
        Icons.Default.BarChart
    )

    data object History : Destination(
        "history",
        "History",
        Icons.Default.History
    )

    data object Settings : Destination(
        "settings",
        "Settings",
        Icons.Default.Settings
    )
    data object Developer : Destination(
        route = "developer",
        title = "Developer",
        icon = Icons.Outlined.Build
    )

    data object CameraHeartRate : Destination(
        "camera_heart_rate",
        "Camera Heart Rate",
        Icons.Default.Favorite
    )

}