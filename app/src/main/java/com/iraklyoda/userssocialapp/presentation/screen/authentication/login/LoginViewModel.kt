package com.iraklyoda.userssocialapp.presentation.screen.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraklyoda.userssocialapp.domain.common.AuthFieldErrorType
import com.iraklyoda.userssocialapp.domain.common.Resource
import com.iraklyoda.userssocialapp.domain.use_case.auth.LogInUserUseCase
import com.iraklyoda.userssocialapp.domain.use_case.validation.ValidateEmailUseCase
import com.iraklyoda.userssocialapp.domain.use_case.validation.ValidatePasswordUseCase
import com.iraklyoda.userssocialapp.presentation.screen.authentication.login.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInUserUseCase: LogInUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    var uiState by mutableStateOf(LoginUiState())
        private set

    private val _sideEffect = Channel<LoginSideEffect>()
    val sideEffect: Flow<LoginSideEffect> = _sideEffect.receiveAsFlow()

    fun onEvent(event: LoginEvent) {

        when (event) {
            // Email Validation
            is LoginEvent.EmailChanged -> onEmailChange(email = event.email)

            // Password Validation
            is LoginEvent.PasswordChanged -> onPasswordChange(password = event.password)

            // Remember Me Handling
            is LoginEvent.RememberMeChanged -> onRememberMeToggle(rememberMe = event.rememberMe)

            // Submit Form
            is LoginEvent.Submit -> submitLoginData()

            // Register Navigation
            is LoginEvent.RegisterBtnClicked -> onRegisterBtnClick()

            // Set Credentials
            is LoginEvent.SetCredentials -> onCredentialsReceive(
                email = event.email,
                password = event.password
            )
        }
    }

    // Ui Events

    // Register Clicked
    private fun onRegisterBtnClick() {
        viewModelScope.launch {
            _sideEffect.send(LoginSideEffect.NavigateToRegister)
        }
    }

    // Credentials Received from registration screen
    private fun onCredentialsReceive(email: String, password: String) {
        uiState = uiState.copy(email = email, password = password)
    }

    // Email Changed
    private fun onEmailChange(email: String) {
        uiState = uiState.copy(email = email)

        val emailError =
            if (state.formBeenSubmitted) validateEmailUseCase(email = email) else null
        state = state.copy(emailError = emailError)
    }

    // Password Changed
    private fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password)

        val passwordError =
            if (state.formBeenSubmitted) validatePasswordUseCase(password = password) else null
        state = state.copy(passwordError = passwordError)
    }

    // Remember me toggled
    private fun onRememberMeToggle(rememberMe: Boolean) {
        uiState = uiState.copy(rememberMe = rememberMe)

    }

    // Form Submitted
    private fun submitLoginData() {
        if (validateForm(
                email = uiState.email,
                password = uiState.password
            )
        )
            login(
                email = uiState.email,
                password = uiState.password,
                rememberMe = uiState.rememberMe
            )
        else
            state = state.copy(formBeenSubmitted = true)
    }

    // Form Validation
    private fun validateForm(
        email: String, password: String
    ): Boolean {
        val emailError = validateEmailUseCase(email)
        val passwordError = validatePasswordUseCase(password)

        state = state.copy(
            emailError = emailError,
            passwordError = passwordError
        )

        val errors: List<AuthFieldErrorType?> = listOf(emailError, passwordError)

        return errors.all { it == null }
    }

    // Data Api Call
    fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            logInUserUseCase(
                email = email,
                password = password,
                rememberMe = rememberMe
            ).collect { resource ->
                when (resource) {
                    is Resource.Loader -> state = state.copy(loader = resource.loading)

                    is Resource.Success -> {
                        state = state.copy(loginSession = resource.data.toUi())
                        _sideEffect.send(LoginSideEffect.NavigateToHome)
                    }

                    is Resource.Error -> {
                        state = state.copy(apiError = resource.errorMessage)
                        _sideEffect.send(LoginSideEffect.ShowAuthError(message = resource.errorMessage))
                    }
                }
            }
        }
    }
}

