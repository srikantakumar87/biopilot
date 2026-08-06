package io.github.srikantakumar87.biopilot.feature.camera.heartrate

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import io.github.srikantakumar87.biopilot.core.camera.model.WavePoint
import androidx.compose.material3.MaterialTheme

@Composable
fun WaveformView(

    waveform: List<WavePoint>,

    modifier: Modifier = Modifier

) {

    val waveformColor = MaterialTheme.colorScheme.primary

    Canvas(

        modifier = modifier

    ) {

        if (waveform.size < 2)
            return@Canvas

        val maxY = waveform.maxOf { it.y }

        val minY = waveform.minOf { it.y }

        val range = (maxY - minY).coerceAtLeast(1f)

        val xScale = size.width / (waveform.size - 1)



        for (i in 1 until waveform.size) {

            val previous = waveform[i - 1]

            val current = waveform[i]

            drawLine(

                color = waveformColor,

                start = Offset(

                    (i - 1) * xScale,

                    size.height -
                            ((previous.y - minY) / range) * size.height
                ),

                end = Offset(

                    i * xScale,

                    size.height -
                            ((current.y - minY) / range) * size.height
                ),

                strokeWidth = 3f
            )
        }
    }
}