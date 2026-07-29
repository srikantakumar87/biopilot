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
            try {
                val steps = repository.getTodaySteps()
                val heartRate = repository.getLatestHeartRate()
                val sleepHours = repository.getTodaySleepHours()
                val weight = repository.getLatestWeight()
                val weeklySteps = repository.getWeeklySteps()

                _uiState.update {
                    it.copy(
                        steps = steps,
                        heartRate = heartRate,
                        sleep = formatSleep(sleepHours),
                        weight = weight,
                        weeklySteps = weeklySteps
                    )
                }
            } catch (e: Exception) {
                Log.e("BioPilot", "Failed to refresh health data", e)
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