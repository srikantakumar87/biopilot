package io.github.srikantakumar87.biopilot.feature.camera.heartrate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MeasuringView(

    uiState: CameraHeartRateUiState

) {

    Column(

        modifier = Modifier.fillMaxWidth()
    ) {

        Text(

            text = "${uiState.bpm ?: "--"} BPM",

            style =
                MaterialTheme.typography.displayLarge
        )

        LinearProgressIndicator(

            progress = {

                uiState.measurementProgress
            },

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(

            Modifier.height(12.dp)
        )

        Text(

            "${uiState.elapsedSeconds} / 30 sec"
        )

        Spacer(

            Modifier.height(16.dp)
        )

        WaveformView(

            waveform = uiState.waveform,

            modifier = Modifier

                .fillMaxWidth()

                .height(120.dp)
        )
    }
}