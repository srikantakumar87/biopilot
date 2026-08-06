package io.github.srikantakumar87.biopilot.core.camera.model

data class PPGSample(

    val timestamp: Long,

    // RGB channels
    val red: Double,

    val green: Double,

    val blue: Double,

    // Raw value from the camera
    val raw: Double,

    // After DC removal
    val dcRemoved: Double = 0.0,

    // After normalization (-1..1)
    val normalized: Double = 0.0,

    // After smoothing / filtering
    val smoothed: Double = 0.0
)