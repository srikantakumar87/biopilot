package io.github.srikantakumar87.biopilot.core.model

import java.time.LocalDate

data class DailySleep(
    val date: LocalDate,
    val dayLabel: String,
    val hours: Double
)