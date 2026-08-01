package io.github.srikantakumar87.biopilot.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.srikantakumar87.biopilot.core.health.HealthConnectManager
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

    fun refreshHealthData() {
        viewModelScope.launch {

            coroutineScope {

                val stepsDeferred = async {
                    repository.getTodaySteps()
                }

                val sleepDeferred = async {
                    repository.getTodaySleepHours()
                }

                val heartRateDeferred = async {
                    repository.getLatestHeartRate()
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

                val steps = stepsDeferred.await()
                val sleepHours = sleepDeferred.await()
                val heartRate = heartRateDeferred.await()
                val weight = weightDeferred.await()
                val weeklySteps = weeklyStepsDeferred.await()
                val weeklySleep = weeklySleepDeferred.await()

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
                        heartRate = heartRate,
                        weight = weight,
                        weeklySteps = weeklySteps,
                        weeklySleep = weeklySleep,
                        averageSleepHours = averageSleepHours
                    )
                }
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