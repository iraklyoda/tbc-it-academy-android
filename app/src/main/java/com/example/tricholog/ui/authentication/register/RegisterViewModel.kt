package com.example.tricholog.ui.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tricholog.data.common.Resource
import com.example.tricholog.data.repositories.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registerStateFlow: MutableStateFlow<RegisterUiState> =
        MutableStateFlow(RegisterUiState.Idle)
    val registerStateFlow: StateFlow<RegisterUiState> get() = _registerStateFlow

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signUp(email, password).collectLatest { resource ->
                when(resource) {
                    is Resource.Loading -> {
                        _registerStateFlow.value = RegisterUiState.Loading
                    }
                    is Resource.Success -> {
                        _registerStateFlow.value = RegisterUiState.Success()
                    }
                    is Resource.Error -> {
                        _registerStateFlow.value = RegisterUiState.Error(resource.error)
                    }
                }
            }
        }
    }
}