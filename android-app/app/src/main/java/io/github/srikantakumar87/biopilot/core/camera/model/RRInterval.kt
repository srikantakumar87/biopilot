package io.github.srikantakumar87.biopilot.core.camera.model

data class RRInterval(

    val previousPeak: Long,

    val currentPeak: Long,

    val intervalMillis: Long
)