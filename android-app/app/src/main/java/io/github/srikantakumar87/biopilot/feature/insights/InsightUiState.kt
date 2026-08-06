package io.github.srikantakumar87.biopilot.feature.insights

import io.github.srikantakumar87.biopilot.core.ai.model.AIReport
import io.github.srikantakumar87.biopilot.core.ai.model.HealthInsight

data class InsightUiState(

    val isLoading: Boolean = false,

    val insights: List<HealthInsight> = emptyList(),
    val error: String? = null,
    val report: AIReport? = null,
)