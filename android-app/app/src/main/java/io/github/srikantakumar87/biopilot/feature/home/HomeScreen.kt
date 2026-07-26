package io.github.srikantakumar87.biopilot.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.srikantakumar87.biopilot.core.designsystem.SectionHeader
import io.github.srikantakumar87.biopilot.feature.home.components.GreetingCard
import io.github.srikantakumar87.biopilot.feature.home.components.HealthSummaryCard
import io.github.srikantakumar87.biopilot.feature.home.components.MetricGrid
import io.github.srikantakumar87.biopilot.feature.home.components.QuickActionsRow

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        GreetingCard(
            userName = uiState.userName
        )

        HealthSummaryCard(
            score = uiState.readinessScore,
            message = "Recovery is good today"
        )
        Spacer(Modifier.height(24.dp))

        SectionHeader("Today's Metrics")

        Spacer(Modifier.height(12.dp))

        MetricGrid(metrics = uiState.metrics)

        SectionHeader("Quick Actions")

        Spacer(Modifier.height(12.dp))

        Text("Actions: ${uiState.quickActions.size}")
        QuickActionsRow(
            actions = uiState.quickActions
        )
    }
}
