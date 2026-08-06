package io.github.srikantakumar87.biopilot.core.camera

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import io.github.srikantakumar87.biopilot.core.camera.model.PPGSample

class CameraFrameAnalyzer(

    private val onSample: (PPGSample) -> Unit

) : ImageAnalysis.Analyzer {

    companion object {

        private const val ROI_SIZE = 80
    }

    override fun analyze(image: ImageProxy) {
        android.util.Log.d(
            "BioPilot",
            "Frame received: ${image.width} x ${image.height}"
        )

        val plane = image.planes[0]

        val buffer = plane.buffer

        val bytes = ByteArray(buffer.remaining())

        buffer.get(bytes)

        val width = image.width

        val height = image.height

        val stride = plane.rowStride

        val startX = (width - ROI_SIZE) / 2

        val startY = (height - ROI_SIZE) / 2

        var red = 0L
        var green = 0L
        var blue = 0L

        var count = 0

        for (y in startY until startY + ROI_SIZE) {

            val row = y * stride

            for (x in startX until startX + ROI_SIZE) {

                val index = row + x * 4

                if (index + 2 >= bytes.size)
                    continue

                red += bytes[index].toInt() and 0xFF

                green += bytes[index + 1].toInt() and 0xFF

                blue += bytes[index + 2].toInt() and 0xFF

                count++
            }
        }

        if (count > 0) {

            val avgRed = red.toDouble() / count

            val avgGreen = green.toDouble() / count

            val avgBlue = blue.toDouble() / count

            android.util.Log.d(
                "BioPilot",
                "R=$avgRed G=$avgGreen B=$avgBlue"
            )

            onSample(

                PPGSample(

                    timestamp = System.currentTimeMillis(),

                    raw = avgRed,

                    red = avgRed,

                    green = avgGreen,

                    blue = avgBlue
                )
            )
        }

        image.close()
    }
}