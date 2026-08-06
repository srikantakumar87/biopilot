package io.github.srikantakumar87.biopilot.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.srikantakumar87.biopilot.core.ai.HealthAnalyzer
import io.github.srikantakumar87.biopilot.core.ai.model.HealthSnapshot
import io.github.srikantakumar87.biopilot.core.health.HealthConnectManager
import io.github.srikantakumar87.biopilot.core.health.HealthDataSeeder
import io.github.srikantakumar87.biopilot.core.health.HealthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val healthConnectManager: HealthConnectManager,
    private val repository: HealthRepository,
    private val healthAnalyzer: HealthAnalyzer,
    private val healthDataSeeder: HealthDataSeeder

    ) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _permissionState = MutableStateFlow(
        HealthPermissionState()
    )


    val permissionState = _permissionState.asStateFlow()




    fun onPermissionsGranted() {
        _permissionState.update {
            it.copy(hasPermissions = true)
        }

        refreshHealthData()
    }

    private fun formatSleep(hours: Double): String {

        val totalMinutes = (hours * 60).toInt()

        val h = totalMinutes / 60
        val m = totalMinutes % 60

        return "${h}h ${m}m"
    }

    fun insertDemoSteps() {
        viewModelScope.launch {
            healthDataSeeder.insertTodaySteps(8500)
            refreshHealthData()
        }
    }

    fun refreshHealthData() {
        viewModelScope.launch {

            coroutineScope {

                val weeklyHeartRatesDeferred = async {
                    repository.getWeeklyHeartRates()
                }
                val heartSummary = async {
                    repository.getHeartRateSummary()
                }


                val stepsDeferred = async {
                    repository.getTodaySteps()
                }

                val sleepDeferred = async {
                    repository.getTodaySleepHours()
                }



                val weightDeferred = async {
                    repository.getLatestWeight()
                }

                val weeklyStepsDeferred = async {
                    repository.getWeeklySteps()
                }

                val weeklySleepDeferred = async {
                    repository.getWeeklySleep()
                }
                val weeklyWeightsDeferred = async {
                    repository.getWeeklyWeights()
                }
                val bodyCompositionDeferred = async {
                    repository.getBodyComposition()
                }

                val steps = stepsDeferred.await()
                val sleepHours = sleepDeferred.await()
                val weeklyWeights = weeklyWeightsDeferred.await()

                val weight = weightDeferred.await()
                val weeklySteps = weeklyStepsDeferred.await()
                val weeklySleep = weeklySleepDeferred.await()
                val heartSummaryResult = heartSummary.await()
                val weeklyHeartRates = weeklyHeartRatesDeferred.await()
                val bodyComposition = bodyCompositionDeferred.await()

                val averageSleepHours =
                    if (weeklySleep.isEmpty()) {
                        0.0
                    } else {
                        weeklySleep.map { it.hours }.average()
                    }

                _uiState.update {
                    it.copy(
                        steps = steps,
                        sleep = formatSleep(sleepHours),
                        heartRate = heartSummaryResult.latest,
                        weight = weight,
                        weeklySteps = weeklySteps,
                        weeklySleep = weeklySleep,
                        averageSleepHours = averageSleepHours,
                        latestHeartRate = heartSummaryResult.latest,
                        weeklyHeartAverage = heartSummaryResult.weeklyAverage,
                        restingHeartRate = heartSummaryResult.restingEstimate,
                        weeklyHeartRates = weeklyHeartRates,
                        weeklyWeights = weeklyWeights,
                        bodyComposition = bodyComposition,
                    )
                }

                val state = _uiState.value

                val snapshot = HealthSnapshot(
                    steps = state.steps,
                    averageSleepHours = state.averageSleepHours,
                    latestHeartRate = state.latestHeartRate,
                    weight = state.weight,
                    bmi = state.bmi
                )

                val analysis = healthAnalyzer.analyze(snapshot)

                Log.d("BioPilotAI", "Steps = ${state.steps}")
                Log.d("BioPilotAI", "Step Goal = ${state.stepGoal}")
                Log.d("BioPilotAI", "Step Progress = ${state.stepProgress}")

                Log.d("BioPilotAI", "Average Sleep = ${state.averageSleepHours}")

                Log.d("BioPilotAI", "Latest HR = ${state.latestHeartRate}")

                Log.d("BioPilotAI", "Weight = ${state.weight}")

                Log.d("BioPilotAI", "Overall Score: ${analysis.overallScore}")
                Log.d("BioPilotAI", "Steps: ${analysis.stepScore}")
                Log.d("BioPilotAI", "Sleep: ${analysis.sleepScore}")
                Log.d("BioPilotAI", "Heart: ${analysis.heartScore}")
                Log.d("BioPilotAI", "Weight: ${analysis.weightScore}")
                Log.d("BioPilotAI", analysis.toString())
            }
        }
    }

    init {
        val available = healthConnectManager.isAvailable()

        _permissionState.value = HealthPermissionState(
            isHealthConnectAvailable = available,
            hasPermissions = false,
            isLoading = false
        )

        Log.d("BioPilot", "Health Connect Available: $available")
    }
}