package com.example.tricholog.ui.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _loginStateFlow: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Idle)
    val loginStateFlow: StateFlow<LoginUiState> get() = _loginStateFlow

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password).collectLatest { resource ->
                when(resource) {
                    is Resource.Error -> {
                        _loginStateFlow.value = LoginUiState.Error(resource.error)
                    }
                    is Resource.Loading -> {
                        _loginStateFlow.value = LoginUiState.Loading
                    }
                    is Resource.Success -> {
                        _loginStateFlow.value = LoginUiState.Success()
                    }
                }

            }
        }
    }

}