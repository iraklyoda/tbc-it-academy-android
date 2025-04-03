package com.iraklyoda.userssocialapp.presentation.screen.authentication.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraklyoda.userssocialapp.domain.common.AuthFieldErrorType
import com.iraklyoda.userssocialapp.domain.common.Resource
import com.iraklyoda.userssocialapp.domain.use_case.auth.SignUpUserUseCase
import com.iraklyoda.userssocialapp.domain.use_case.validation.ValidateEmailUseCase
import com.iraklyoda.userssocialapp.domain.use_case.validation.ValidatePasswordUseCase
import com.iraklyoda.userssocialapp.domain.use_case.validation.ValidateRepeatedPasswordUseCase
import com.iraklyoda.userssocialapp.presentation.screen.authentication.register.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUserUseCase: SignUpUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    var uiState by mutableStateOf(RegisterUiState())
        private set

    private val _sideEffect = Channel<RegisterSideEffect>()
    val sideEffect: Flow<RegisterSideEffect> = _sideEffect.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            // Email Validation
            is RegisterEvent.EmailChanged -> onEmailChanged(email = event.email)

            // Password Validation
            is RegisterEvent.PasswordChanged -> onPasswordChanged(password = event.password)

            // Repeated Password Validation
            is RegisterEvent.RepeatedPasswordChanged -> onRepeatedPasswordChanged(repeatedPassword = event.repeatedPassword)

            // Submit Form
            is RegisterEvent.Submit -> submitRegisterData()
        }
    }

    // Ui Events

    // Email Changed
    private fun onEmailChanged(email: String) {
        uiState = uiState.copy(email = email)

        val emailError =
            if (state.formBeenSubmitted) validateEmailUseCase(email = email) else null
        state = state.copy(emailError = emailError)
    }

    // Password Changed
    private fun onPasswordChanged(password: String) {
        uiState = uiState.copy(password = password)

        val passwordError =
            if (state.formBeenSubmitted) validatePasswordUseCase(password) else null

        val repeatedPasswordError =
            if (state.formBeenSubmitted) validateRepeatedPasswordUseCase(
                password = password,
                repeatedPassword = uiState.repeatedPassword
            ) else null

        state = state.copy(
            passwordError = passwordError,
            repeatedPasswordError = repeatedPasswordError
        )
    }


    // Repeated Password Changed
    private fun onRepeatedPasswordChanged(repeatedPassword: String) {
        uiState = uiState.copy(repeatedPassword = repeatedPassword)

        val repeatedPasswordError =
            if (state.formBeenSubmitted) validateRepeatedPasswordUseCase(
                password = uiState.password,
                repeatedPassword = repeatedPassword
            ) else null

        state = state.copy(repeatedPasswordError = repeatedPasswordError)
    }

    // Form Submitted
    private fun submitRegisterData() {
        if (validateForm(uiState.email, uiState.password, uiState.repeatedPassword))
            register(
                email = uiState.email,
                password = uiState.password
            )
        else {
            state = state.copy(formBeenSubmitted = true)
        }
    }

    // Form Validation
    private fun validateForm(email: String, password: String, repeatedPassword: String): Boolean {
        val emailError = validateEmailUseCase(email)
        val passwordError = validatePasswordUseCase(password)
        val repeatedPasswordError = validateRepeatedPasswordUseCase(
            password = password,
            repeatedPassword = repeatedPassword
        )

        state = state.copy(
            emailError = emailError,
            passwordError = passwordError,
            repeatedPasswordError = repeatedPasswordError
        )

        val errors: List<AuthFieldErrorType?> =
            listOf(emailError, passwordError, repeatedPasswordError)

        return errors.all { it == null }
    }

    // Data Api Call
    private fun register(email: String, password: String) {
        viewModelScope.launch {
            signUpUserUseCase(
                email = email,
                password = password
            ).collectLatest { resource ->
                when (resource) {
                    is Resource.Loader -> state = state.copy(loader = resource.loading)

                    is Resource.Success -> {
                        state = state.copy(registerSession = resource.data.toUi())
                        _sideEffect.send(
                            RegisterSideEffect.NavigateToLogin(
                                email = email,
                                password = password
                            )
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(apiError = resource.errorMessage)
                        _sideEffect.send(RegisterSideEffect.ShowApiError(resource.errorMessage))
                    }
                }
            }
        }
    }
}