package io.github.srikantakumar87.biopilot.feature.home

import android.util.Log
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
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Box
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.health.connect.client.PermissionController
import io.github.srikantakumar87.biopilot.core.health.HealthPermissions
import androidx.compose.runtime.LaunchedEffect
import io.github.srikantakumar87.biopilot.core.model.HealthMetric
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import io.github.srikantakumar87.biopilot.core.designsystem.BioPilotCard
import io.github.srikantakumar87.biopilot.feature.home.components.SleepInsightsCard
import io.github.srikantakumar87.biopilot.feature.home.components.StepGoalCard
import io.github.srikantakumar87.biopilot.feature.home.components.WeeklyActivityCard
import io.github.srikantakumar87.biopilot.feature.home.components.WeeklyStepsChart

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    val metrics = listOf(
        HealthMetric(
            title = "Steps",
            value = uiState.steps.toString(),
            unit = "steps",
            icon = Icons.Outlined.DirectionsWalk
        ),
        HealthMetric(
            title = "Sleep",
            value = uiState.sleep,
            unit = "",
            icon = Icons.Outlined.Bedtime
        ),
        HealthMetric(
            title = "Heart Rate",
            value = uiState.heartRate?.toString() ?: "--",
            unit = "bpm",
            icon = Icons.Outlined.Favorite
        ),
        HealthMetric(
            title = "Weight",
            value = uiState.weight?.let { "%.1f".format(it) } ?: "--",
            unit = "kg",
            icon = Icons.Outlined.MonitorWeight
        )
    )
    val permissionState by viewModel.permissionState.collectAsStateWithLifecycle()
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.background
        )
    )

    val permissionLauncher =
        rememberLauncherForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { grantedPermissions ->

            Log.d("BioPilot", "Granted permissions: $grantedPermissions")

            if (grantedPermissions.containsAll(HealthPermissions.permissions)) {
                Log.d("BioPilot", "All permissions granted")
                viewModel.onPermissionsGranted()
            } else {
                Log.d("BioPilot", "Permissions NOT granted")
            }
        }
    LaunchedEffect(permissionState.isHealthConnectAvailable) {

        if (permissionState.isHealthConnectAvailable) {
            Log.d("BioPilot", "Launching Health Connect permission request")
            permissionLauncher.launch(
                HealthPermissions.permissions

            )
        }
        else{
            Log.d("BioPilot", "Health Connect not available")
        }
    }
    if (!permissionState.isHealthConnectAvailable) {

        Text(
            text = "Health Connect is not available on this device."
        )

        return
    }

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->

            if (event == Lifecycle.Event.ON_RESUME &&
                permissionState.hasPermissions
            ) {
                Log.d("BioPilot", "ON_RESUME -> Refreshing Health Connect data")
                viewModel.refreshHealthData()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
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
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                StepGoalCard(
                    steps = uiState.steps,
                    goal = uiState.stepGoal,
                    progress = uiState.stepProgress
                )
                Spacer(Modifier.height(16.dp))

                BioPilotCard {

                    Column {

                        Text(
                            text = "Weekly Steps",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(16.dp))
                        WeeklyStepsChart(
                            weeklySteps = uiState.weeklySteps
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                WeeklyActivityCard(
                    weeklySteps = uiState.weeklySteps
                )

                Spacer(modifier = Modifier.height(16.dp))

                WeeklyActivityCard(
                    weeklySteps = uiState.weeklySteps
                )

                Spacer(modifier = Modifier.height(16.dp))


            }
            SleepInsightsCard(
                averageSleepHours = uiState.averageSleepHours
            )

            Spacer(modifier = Modifier.height(16.dp))

            SectionHeader("Today's Metrics")

            Spacer(Modifier.height(12.dp))

            MetricGrid(metrics = metrics)

            SectionHeader("Quick Actions")

            Spacer(Modifier.height(12.dp))


            QuickActionsRow(
                actions = uiState.quickActions
            )
        }
    }
}
