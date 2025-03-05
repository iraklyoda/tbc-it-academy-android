package com.example.tricholog.ui.dashboard.logs.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.repository.TrichoLogRepository
import com.example.tricholog.ui.mapper.toTrichoLogUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor(
    private val trichoLogRepository: TrichoLogRepository
) : ViewModel() {

    private val _logsStateFlow: MutableStateFlow<LogsUiState> = MutableStateFlow(LogsUiState.Idle)
    val logsStateFlow: StateFlow<LogsUiState> get() = _logsStateFlow

    fun getLogs() {
        viewModelScope.launch {
            trichoLogRepository.getTrichoLogs().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _logsStateFlow.value = LogsUiState.Loading
                    }

                    is Resource.Success -> {
                        _logsStateFlow.value = LogsUiState.Success(resource.data.map { it.toTrichoLogUi() })
                    }

                    is Resource.Error -> {
                        _logsStateFlow.value = LogsUiState.Error(resource.error)
                    }
                }
            }
        }
    }

    init {
        getLogs()
    }
}