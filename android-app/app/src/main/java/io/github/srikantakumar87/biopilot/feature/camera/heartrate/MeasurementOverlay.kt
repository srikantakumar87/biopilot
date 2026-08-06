package io.github.srikantakumar87.biopilot.feature.camera.heartrate

import androidx.compose.runtime.Composable
import io.github.srikantakumar87.biopilot.core.camera.model.MeasurementState

@Composable
fun MeasurementOverlay(

    uiState: CameraHeartRateUiState

) {

    when (uiState.measurementState) {

        MeasurementState.WAITING_FOR_FINGER ->
            WaitingForFingerView()

        MeasurementState.STABILIZING ->
            StabilizingView(uiState)

        MeasurementState.MEASURING ->
            MeasuringView(uiState)

        MeasurementState.PROCESSING ->
            ProcessingView(
                progress = uiState.processingProgress
            )

        MeasurementState.COMPLETE ->
            ResultView(uiState)

        else -> {}
    }
}