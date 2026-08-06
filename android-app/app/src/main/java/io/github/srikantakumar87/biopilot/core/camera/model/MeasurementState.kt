package io.github.srikantakumar87.biopilot.core.camera.model

enum class MeasurementState {

    WAITING_FOR_FINGER,

    DETECTING,
    STABILIZING,

    MEASURING,

    PROCESSING,

    COMPLETE,

    ERROR
}