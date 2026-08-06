package io.github.srikantakumar87.biopilot.feature.camera.heartrate

import androidx.camera.core.ImageAnalysis
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.camera.view.PreviewView
import io.github.srikantakumar87.biopilot.core.camera.CameraController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraController: CameraController,
    analyzer: ImageAnalysis.Analyzer
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember {
        PreviewView(context)
    }

    DisposableEffect(Unit) {

        CoroutineScope(Dispatchers.Main).launch {

            cameraController.startCamera(
                context = context,
                lifecycleOwner = lifecycleOwner,
                previewView = previewView,
                analyzer = analyzer
            )
        }

        onDispose {
            cameraController.stopCamera()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            previewView
        }
    )
}