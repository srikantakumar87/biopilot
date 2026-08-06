package io.github.srikantakumar87.biopilot.feature.camera.heartrate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StabilizingView(

    uiState: CameraHeartRateUiState

) {

    Column(

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center,

        modifier = Modifier.fillMaxSize()

    ) {

        CircularProgressIndicator(

            progress = {

                uiState.measurementProgress
            }
        )

        Spacer(

            Modifier.height(24.dp)
        )

        Text(

            "Hold Still"
        )

        Text(

            "Stabilizing signal..."
        )
    }
}