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
import com.iraklyoda.userssocialapp.presentation.screen.authentication.mapper.mapToStringResource
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

            // Password Visibility Toggle
            is LoginEvent.TogglePasswordVisibility -> onPasswordVisibilityToggle()

            // Remember Me Handling
            is LoginEvent.ToggleRememberMe -> onRememberMeToggle()

            // Submit Form
            is LoginEvent.Submit -> submitLoginData()

            // Register Navigation
            is LoginEvent.NavigateToRegister -> navigateToRegister()

            // Set Credentials
            is LoginEvent.SetCredentials -> onCredentialsReceive(
                email = event.email,
                password = event.password
            )
        }
    }

    // Ui Events

    // Register Clicked
    private fun navigateToRegister() {
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
        state = state.copy(emailErrorResource = emailError?.mapToStringResource())
    }

    // Password Changed
    private fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password)

        val passwordError =
            if (state.formBeenSubmitted) validatePasswordUseCase(password = password) else null
        state = state.copy(passwordErrorResource = passwordError?.mapToStringResource())
    }

    // Toggle Password Visibility
    private fun onPasswordVisibilityToggle() {
        uiState = uiState.copy(passwordVisible = !uiState.passwordVisible)
    }

    // Remember me toggled
    private fun onRememberMeToggle() {
        uiState = uiState.copy(rememberMe = !uiState.rememberMe)
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
        val emailError: AuthFieldErrorType? = validateEmailUseCase(email)
        val passwordError: AuthFieldErrorType? = validatePasswordUseCase(password)

        state = state.copy(
            emailErrorResource = emailError?.mapToStringResource(),
            passwordErrorResource = passwordError?.mapToStringResource()
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

