package io.github.srikantakumar87.biopilot.core.model

import androidx.compose.ui.graphics.vector.ImageVector

data class QuickAction(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit = {}
)