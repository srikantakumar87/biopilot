package io.github.srikantakumar87.biopilot.core.ai.model


data class HealthSnapshot(

    val steps: Long,

    val averageSleepHours: Double,

    val latestHeartRate: Long?,

    val weight: Double?,

    val bmi: Double?
)