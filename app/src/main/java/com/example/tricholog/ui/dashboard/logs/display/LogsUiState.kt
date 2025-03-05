package com.example.tricholog.ui.dashboard.logs.display

import com.example.tricholog.domain.error.ApiError
import com.example.tricholog.domain.model.TrichoLog
import com.example.tricholog.ui.dashboard.logs.model.TrichoLogUi

sealed class LogsUiState {
    data object Idle: LogsUiState()
    data object Loading: LogsUiState()
    data class Success(val logs: List<TrichoLogUi>): LogsUiState()
    data class Error(val error: ApiError): LogsUiState()
}