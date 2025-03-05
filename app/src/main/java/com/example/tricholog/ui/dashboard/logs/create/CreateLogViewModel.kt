package com.example.tricholog.ui.dashboard.logs.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.model.TrichoLog
import com.example.tricholog.domain.repository.TrichoLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateLogViewModel @Inject constructor(
    private val trichoLogRepository: TrichoLogRepository
) : ViewModel() {

    private val _logStateFlow: MutableStateFlow<CreateLogState> =
        MutableStateFlow(CreateLogState.Idle)
    val logStateFlow: StateFlow<CreateLogState> get() = _logStateFlow

    fun createLog(trigger: String, body: String) {
        val log = TrichoLog(
            trigger = trigger,
            body = body,
            createdAt = System.currentTimeMillis()
        )

        viewModelScope.launch {
            trichoLogRepository.saveTrichoLog(log).collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _logStateFlow.value = CreateLogState.Error(resource.error)
                    }

                    is Resource.Loading -> {
                        _logStateFlow.value = CreateLogState.Loading
                    }

                    is Resource.Success -> {
                        _logStateFlow.value = CreateLogState.Success()
                    }
                }
            }
        }
    }


}