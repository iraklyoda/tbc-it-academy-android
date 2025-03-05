package com.example.tricholog.ui.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tricholog.domain.model.User
import com.example.tricholog.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log
import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.model.TimeData
import com.example.tricholog.domain.repository.TrichoLogRepository
import com.example.tricholog.ui.dashboard.logs.display.LogsUiState
import com.example.tricholog.ui.mapper.toTrichoLogUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import java.util.Locale

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trichoLogRepository: TrichoLogRepository
) : ViewModel() {

    private val _logsStateFlow: MutableStateFlow<LogsUiState> = MutableStateFlow(LogsUiState.Idle)
    val logsStateFlow: StateFlow<LogsUiState> get() = _logsStateFlow

    private val _timerStateFlow: MutableStateFlow<TimeData> = MutableStateFlow(TimeData(0, 0, 0, 0))
    val timerStateFlow: StateFlow<TimeData> get() = _timerStateFlow

    private var lastLogTimestamp: Long = 0L
    private var timerJob: Job? = null

    fun getLogs() {
        viewModelScope.launch {
            trichoLogRepository.getTrichoLogs().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _logsStateFlow.value = LogsUiState.Loading
                    }

                    is Resource.Success -> {
                        _logsStateFlow.value = LogsUiState.Success(resource.data.map { it.toTrichoLogUi() })

                        lastLogTimestamp = resource.data.lastOrNull()?.createdAt ?: 0L
                        Log.d("LastLogTimestamp", lastLogTimestamp.toString())
                        startTimer()
                    }

                    is Resource.Error -> {
                        _logsStateFlow.value = LogsUiState.Error(resource.error)
                    }
                }
            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()

        timerJob = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - lastLogTimestamp

                val days = elapsedTime / (1000 * 60 * 60 * 24)
                val hours = (elapsedTime / (1000 * 60 * 60)) % 24
                val minutes = (elapsedTime / (1000 * 60)) % 60
                val seconds = (elapsedTime / 1000) % 60

                _timerStateFlow.value = TimeData(days, hours, minutes, seconds)

                delay(1000)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    init {
        getLogs()
    }
}