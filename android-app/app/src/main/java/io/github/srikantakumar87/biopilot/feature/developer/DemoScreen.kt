package io.github.srikantakumar87.biopilot.feature.developer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import io.github.srikantakumar87.biopilot.navigation.Destination

@Composable
fun DemoScreen(
    navController: NavHostController,
    viewModel: DemoViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            "Developer Tools",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = {
                viewModel.insertSteps()
            }
        ) {
            Text("Insert Steps")
        }
        Button(
            onClick = { viewModel.insertSleep() }
        ) {
            Text("Insert Sleep")
        }

        Button(
            onClick = {
                viewModel.insertHeartRate()
            }
        ) {
            Text("Insert Heart Rate")
        }

        Button(
            onClick = { viewModel.insertWeight() }
        ) {
            Text("Insert Weight")
        }

        Button(
            onClick = { viewModel.seedWeek() }
        ) {
            Text("Seed Full Week")
        }

        Button(
            onClick = {
                viewModel.clearDemoData()
            }
        ) {
            Text("Clear Demo Data")
        }

        Button(
            onClick = {
                navController.navigate(
                    Destination.CameraHeartRate.route
                )
            }
        ) {
            Text("Camera Heart Rate")
        }

        Text(uiState.status)
    }
}