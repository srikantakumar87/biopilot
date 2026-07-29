package io.github.srikantakumar87.biopilot.feature.home

data class HealthPermissionState(
    val isHealthConnectAvailable: Boolean = false,
    val hasPermissions: Boolean = false,
    val isLoading: Boolean = true
)