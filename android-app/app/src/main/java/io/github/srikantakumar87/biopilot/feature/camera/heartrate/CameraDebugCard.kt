package io.github.srikantakumar87.biopilot.feature.camera.heartrate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.srikantakumar87.biopilot.core.camera.model.SignalQuality

@Composable
fun CameraDebugCard(
    modifier: Modifier = Modifier,
    viewModel: CameraHeartRateViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ElevatedCard(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {



            Text("Frames : ${uiState.frameCount}")

            Text(
                "Raw Intensity : %.2f".format(uiState.rawIntensity)
            )

            Text(
                "Filtered : %.2f".format(uiState.filteredIntensity)
            )

            Text("Samples : ${uiState.sampleCount}")

            Text("Peaks : ${uiState.peakCount}")

            Text(
                "BPM : ${uiState.bpm ?: "--"}"
            )

            val qualityText = when (uiState.signalQuality) {
                SignalQuality.NO_SIGNAL -> "🔴 No Signal"
                SignalQuality.POOR -> "🟠 Poor"
                SignalQuality.FAIR -> "🟡 Fair"
                SignalQuality.GOOD -> "🟢 Good"
                SignalQuality.EXCELLENT -> "💚 Excellent"
            }

            Text("Signal Quality: $qualityText")
            Text("Red : %.1f".format(uiState.red))

            Text("Green : %.1f".format(uiState.green))

            Text("Blue : %.1f".format(uiState.blue))

            Text(
                "RR : ${uiState.latestRR ?: "--"} ms"
            )

            Text(
                "RMSSD : ${
                    uiState.rmssd?.let {
                        "%.1f ms".format(it)
                    } ?: "--"
                }"
            )

            Text(
                "SDNN : ${
                    uiState.sdnn?.let {
                        "%.1f ms".format(it)
                    } ?: "--"
                }"
            )

            Text(
                "pNN50 : ${
                    uiState.pnn50?.let {
                        "%.1f %%".format(it)
                    } ?: "--"
                }"
            )




        }
    }
}