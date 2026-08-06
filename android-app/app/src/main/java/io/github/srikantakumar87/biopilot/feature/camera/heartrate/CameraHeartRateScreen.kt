package io.github.srikantakumar87.biopilot.feature.camera.heartrate

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.srikantakumar87.biopilot.core.camera.CameraController
import io.github.srikantakumar87.biopilot.core.camera.CameraFrameAnalyzer

@Composable
fun CameraHeartRateScreen(
    viewModel: CameraHeartRateViewModel = hiltViewModel()
) {

    val cameraController = remember {
        CameraController()
    }

    val analyzer = remember {
        CameraFrameAnalyzer(viewModel::onFrame)
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    val view = LocalView.current

    DisposableEffect(Unit) {

        view.keepScreenOn = true

        onDispose {
            view.keepScreenOn = false
        }
    }

    CameraPermission {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Camera Heart Rate",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Box(
                modifier = Modifier.weight(1f)
            ) {

                // Camera Preview
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    cameraController = cameraController,
                    analyzer = analyzer
                )

                // Bottom Overlay
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {

                    WaveformView(
                        waveform = uiState.waveform,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )



                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    CameraDebugCard(
                        modifier = Modifier.fillMaxWidth(),
                        viewModel = viewModel
                    )

                    /*
                    MeasurementOverlay(

                        uiState = uiState
                    )*/
}
}
}
}
}