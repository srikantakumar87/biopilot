package io.github.srikantakumar87.biopilot.feature.developer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.srikantakumar87.biopilot.core.health.HealthDataSeeder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val seeder: HealthDataSeeder
) : ViewModel() {

    private val _uiState = MutableStateFlow(DemoUiState())
    val uiState = _uiState.asStateFlow()


    private fun launchSeederTask(
        loadingMessage: String,
        successMessage: String,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                status = loadingMessage
            )

            try {

                block()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    status = successMessage
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    status = "Failed: ${e.message}"
                )
            }
        }
    }
    fun insertSteps() = launchSeederTask(
        loadingMessage = "Inserting steps...",
        successMessage = "Steps inserted."
    ) {
        seeder.insertTodaySteps()
    }

    fun insertSleep() = launchSeederTask(
        loadingMessage = "Inserting sleep...",
        successMessage = "Sleep inserted."
    ) {
        seeder.insertTodaySleep()
    }


    fun insertHeartRate() = launchSeederTask(
        loadingMessage = "Inserting heart rate...",
        successMessage = "Heart rate inserted."
    ) {
        seeder.insertTodayHeartRate()
    }


    fun insertWeight() = launchSeederTask(
        loadingMessage = "Inserting weight...",
        successMessage = "Weight inserted."
    ) {
        seeder.insertTodayWeight()
    }

    fun seedWeek() = launchSeederTask(
        loadingMessage = "Seeding weekly demo data...",
        successMessage = "Weekly demo data inserted."
    ) {
        seeder.clearAllDemoData()
        seeder.seedWeek()
    }

    fun clearDemoData() = launchSeederTask(
        loadingMessage = "Clearing demo data...",
        successMessage = "Demo data cleared."
    ) {
        seeder.clearAllDemoData()
    }
}