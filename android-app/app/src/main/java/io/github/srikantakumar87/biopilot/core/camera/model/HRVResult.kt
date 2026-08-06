package io.github.srikantakumar87.biopilot.core.camera.model

data class HRVResult(

    // Root Mean Square of Successive Differences (ms)
    val rmssd: Double,

    // Standard Deviation of NN intervals (ms)
    val sdnn: Double,

    // Percentage of successive RR differences > 50 ms
    val pnn50: Double,

    // Mean RR interval (ms)
    val meanRR: Double,

    // Mean heart rate (BPM)
    val meanHeartRate: Double,

    // Number of RR intervals used
    val intervalCount: Int,

    // Whether the HRV calculation is considered reliable
    val valid: Boolean
)