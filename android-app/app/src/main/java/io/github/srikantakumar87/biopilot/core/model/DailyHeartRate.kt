package io.github.srikantakumar87.biopilot.core.model

import java.time.LocalDate

data class DailyHeartRate(
    val date: LocalDate,
    val dayLabel: String,
    val heartRate: Long
)