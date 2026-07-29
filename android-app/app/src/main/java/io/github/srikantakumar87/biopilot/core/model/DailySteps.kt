package io.github.srikantakumar87.biopilot.core.model

import java.time.LocalDate

data class DailySteps(
    val date: LocalDate,
    val dayLabel: String,
    val steps: Long
)