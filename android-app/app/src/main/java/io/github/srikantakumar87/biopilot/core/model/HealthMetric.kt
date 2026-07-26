package io.github.srikantakumar87.biopilot.core.model

import androidx.compose.ui.graphics.vector.ImageVector

data class HealthMetric(
    val title: String,
    val value: String,
    val unit: String,
    val icon: ImageVector
)