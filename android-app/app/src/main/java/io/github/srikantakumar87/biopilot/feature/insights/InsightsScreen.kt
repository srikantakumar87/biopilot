package io.github.srikantakumar87.biopilot.feature.insights

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import io.github.srikantakumar87.biopilot.core.ai.model.AIReport
import io.github.srikantakumar87.biopilot.core.ai.model.HealthInsight
import androidx.compose.runtime.getValue
import io.github.srikantakumar87.biopilot.core.ai.model.HealthRecommendation

@Composable
fun InsightScreen(
    viewModel: InsightViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    when {

        uiState.isLoading -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(uiState.error!!)
            }
        }

        uiState.report != null -> {

            InsightContent(
                report = uiState.report!!
            )
        }
    }
}

@Composable
private fun InsightContent(
    report: AIReport
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {

        item {

            OverallScoreCard(
                score = report.analysis.overallScore
            )
        }

        items(report.insights) { insight ->

            InsightCard(insight)
        }
        item {

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Recommended Actions",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        items(report.recommendations) { recommendation ->

            RecommendationCard(recommendation)
        }
    }
}

@Composable
fun RecommendationCard(
    recommendation: HealthRecommendation
) {

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = recommendation.title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = recommendation.message
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = recommendation.priority.name
            )
        }
    }
}
@Composable
fun OverallScoreCard(
    score: Int
) {

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                text = "Overall Health Score",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "$score / 100",
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}

@Composable
fun InsightCard(
    insight: HealthInsight
) {

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = insight.title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = insight.message
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = insight.metric ?: ""
            )
        }
    }
}