package io.github.srikantakumar87.biopilot.core.model

import java.time.LocalDate

data class DailyWeight(
    val date: LocalDate,
    val dayLabel: String,
    val weight: Double
)