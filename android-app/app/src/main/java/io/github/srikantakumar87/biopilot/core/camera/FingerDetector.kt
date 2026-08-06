package io.github.srikantakumar87.biopilot.core.camera

import io.github.srikantakumar87.biopilot.core.camera.model.PPGSample
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FingerDetector @Inject constructor() {

    fun hasFinger(sample: PPGSample): Boolean {

        // Strong red dominance indicates a finger over the flash
        if (sample.red < 150.0)
            return false

        if (sample.red < sample.green * 1.3)
            return false

        if (sample.red < sample.blue * 1.3)
            return false

        return true
    }
}