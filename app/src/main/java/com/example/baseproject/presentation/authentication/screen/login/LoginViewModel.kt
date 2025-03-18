package com.example.baseproject.presentation.authentication.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.use_case.auth.LogInUserUseCase
import com.example.baseproject.domain.use_case.validation.ValidateEmailUseCase
import com.example.baseproject.domain.use_case.validation.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInUserUseCase: LogInUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> get() = _uiState

    private val _loginEvents = Channel<LoginEvent>()
    val loginEvents = _loginEvents.receiveAsFlow()

    fun handleEvent(event: LoginUiEvents) {
        val formBeenSubmitted = uiState.value.formBeenSubmitted

        when (event) {
            // Email Validation
            is LoginUiEvents.EmailChanged -> _uiState.update {
                val emailError =
                    if (formBeenSubmitted) validateEmailUseCase(event.email) else null

                it.copy(email = event.email, emailError = emailError)
            }

            // Password Validation
            is LoginUiEvents.PasswordChanged -> _uiState.update {
                val passwordError =
                    if (formBeenSubmitted) validatePasswordUseCase(event.password) else null
                it.copy(password = event.password, passwordError = passwordError)
            }

            is LoginUiEvents.RememberMeChanged -> _uiState.update { it.copy(rememberMe = event.rememberMe) }
            is LoginUiEvents.Submit -> submitLoginData()
        }
    }

    private fun submitLoginData() {
        if (validateForm())
            login()
        else
            _uiState.update { it.copy(formBeenSubmitted = true) }
    }

    private fun validateForm(): Boolean {
        val emailResult = validateEmailUseCase(uiState.value.email)
        val passwordResult = validatePasswordUseCase(uiState.value.password)

        _uiState.update {
            it.copy(
                emailError = emailResult,
                passwordError = passwordResult,
            )
        }

        return emailResult == null && passwordResult == null
    }

    fun login() {
        val email = uiState.value.email
        val password = uiState.value.password
        val rememberMe = uiState.value.rememberMe

        viewModelScope.launch {
            logInUserUseCase(
                email = email,
                password = password,
                rememberMe = rememberMe
            ).collect { resource ->
                when (resource) {
                    is Resource.Loader -> _uiState.update { it.copy(isLoading = resource.loading) }

                    is Resource.Success -> _uiState.update {
                        _loginEvents.send(LoginEvent.LoginSuccess)

                        it.copy(
                            isLoading = false,
                            loginSession = resource.data
                        )
                    }

                    is Resource.Error -> _uiState.update {
                        _loginEvents.send(LoginEvent.LoginError(resource.errorMessage))
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }
}

sealed interface LoginEvent {
    data object LoginSuccess : LoginEvent
    data class LoginError(val message: String?) : LoginEvent
}

