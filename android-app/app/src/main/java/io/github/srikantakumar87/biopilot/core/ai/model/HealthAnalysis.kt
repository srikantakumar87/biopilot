package io.github.srikantakumar87.biopilot.core.ai.model

data class HealthAnalysis(

    val stepScore: Int,

    val sleepScore: Int,

    val heartScore: Int,

    val weightScore: Int,

    val overallScore: Int,

    //val cardiovascularAge: Int,
    //val metabolicScore: Int,
    //val recoveryScore: Int,
)