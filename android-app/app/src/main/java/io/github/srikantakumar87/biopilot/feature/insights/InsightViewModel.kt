package io.github.srikantakumar87.biopilot.feature.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.srikantakumar87.biopilot.core.ai.AIHealthEngine
import io.github.srikantakumar87.biopilot.core.health.HealthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightViewModel @Inject constructor(
    private val repository: HealthRepository,
    private val aiHealthEngine: AIHealthEngine
) : ViewModel() {

    private val _uiState = MutableStateFlow(InsightUiState())

    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {

                val snapshot = repository.getHealthSnapshot()

                val report = aiHealthEngine.generateReport(snapshot)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    report = report,
                    error = null
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}