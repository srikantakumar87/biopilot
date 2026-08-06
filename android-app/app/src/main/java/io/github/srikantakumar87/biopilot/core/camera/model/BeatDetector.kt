package io.github.srikantakumar87.biopilot.core.camera.model


import io.github.srikantakumar87.biopilot.core.camera.model.Beat
import io.github.srikantakumar87.biopilot.core.camera.model.Peak
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeatDetector @Inject constructor() {

    fun detectBeats(
        peaks: List<Peak>
    ): List<Beat> {

        return peaks.map {

            Beat(
                timestamp = it.timestamp
            )
        }
    }
}