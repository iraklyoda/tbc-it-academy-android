package com.example.baseproject.presentation.authentication.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.domain.common.AuthFieldErrorType
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.use_case.auth.SignUpUserUseCase
import com.example.baseproject.domain.use_case.validation.ValidateEmailUseCase
import com.example.baseproject.domain.use_case.validation.ValidatePasswordUseCase
import com.example.baseproject.domain.use_case.validation.ValidateRepeatedPasswordUseCase
import com.example.baseproject.presentation.authentication.screen.register.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUserUseCase: SignUpUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    private val state: StateFlow<RegisterState> get() = _state

    private val _uiEvents = Channel<RegisterUiEvent>()
    val uiEvents: Flow<RegisterUiEvent> = _uiEvents.receiveAsFlow()

    private val uiState = MutableStateFlow(RegisterUiState())

    fun onEvent(event: RegisterEvent) {
        when (event) {
            // Email Validation
            is RegisterEvent.EmailChanged -> onEmailChanged(event = event)

            // Password Validation
            is RegisterEvent.PasswordChanged -> onPasswordChanged(event = event)

            // Repeated Password Validation
            is RegisterEvent.RepeatedPasswordChanged -> onRepeatedPasswordChanged(event = event)

            // Submit Form
            is RegisterEvent.Submit -> submitRegisterData(event = event)
        }
    }

    // Ui Events
    private fun onEmailChanged(event: RegisterEvent.EmailChanged) {
        val emailError =
            if (uiState.value.formBeenSubmitted) validateEmailUseCase(event.email) else null
        uiState.update { it.copy(emailError = emailError) }

        viewModelScope.launch {
            _uiEvents.send(RegisterUiEvent.SetEmailInputError(error = emailError))
            _uiEvents.send(RegisterUiEvent.SetRegisterBtnStatus(isEnabled = uiState.value.isSignUpBtnEnabled))
        }
    }

    private fun onPasswordChanged(event: RegisterEvent.PasswordChanged) {
        val passwordError =
            if (uiState.value.formBeenSubmitted) validatePasswordUseCase(event.password) else null
        uiState.update { it.copy(passwordError = passwordError) }

        val repeatedPasswordError =
            if (uiState.value.formBeenSubmitted) validateRepeatedPasswordUseCase(
                password = event.password,
                repeatedPassword = event.repeatedPassword
            ) else null

        viewModelScope.launch {
            _uiEvents.send(RegisterUiEvent.SetPasswordInputError(error = passwordError))
            _uiEvents.send(RegisterUiEvent.SetRepeatedPasswordInputError(error = repeatedPasswordError))
            _uiEvents.send(RegisterUiEvent.SetRegisterBtnStatus(isEnabled = uiState.value.isSignUpBtnEnabled))
        }
    }

    private fun onRepeatedPasswordChanged(event: RegisterEvent.RepeatedPasswordChanged) {

        val repeatedPasswordError =
            if (uiState.value.formBeenSubmitted) validateRepeatedPasswordUseCase(
                password = event.password,
                repeatedPassword = event.repeatedPassword
            ) else null

        uiState.update { it.copy(repeatedPasswordError = repeatedPasswordError) }

        viewModelScope.launch {
            _uiEvents.send(RegisterUiEvent.SetRepeatedPasswordInputError(error = repeatedPasswordError))
            _uiEvents.send(RegisterUiEvent.SetRegisterBtnStatus(isEnabled = uiState.value.isSignUpBtnEnabled))
        }
    }

    private fun submitRegisterData(event: RegisterEvent.Submit) {
        if (validateForm(event.email, event.password, event.repeatedPassword))
            register(
                email = event.email,
                password = event.password
            )
        else {
            viewModelScope.launch {
                uiState.update { it.copy(formBeenSubmitted = true) }
                _uiEvents.send(RegisterUiEvent.SetRegisterBtnStatus(uiState.value.isSignUpBtnEnabled))
            }
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

        uiState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError,
                repeatedPasswordError = repeatedPasswordError
            )
        }

        viewModelScope.launch {
            _uiEvents.send(RegisterUiEvent.SetEmailInputError(error = uiState.value.emailError))
            _uiEvents.send(RegisterUiEvent.SetPasswordInputError(error = uiState.value.passwordError))
            _uiEvents.send(RegisterUiEvent.SetRepeatedPasswordInputError(error = uiState.value.repeatedPasswordError))
            _uiEvents.send(RegisterUiEvent.SetRegisterBtnStatus(isEnabled = uiState.value.isSignUpBtnEnabled))
        }

        val errors: List<AuthFieldErrorType?> =
            listOf(emailError, passwordError, repeatedPasswordError)

        return errors.any { it != null }.not()
    }

    // Data Api Call
    private fun register(email: String, password: String) {
        viewModelScope.launch {
            signUpUserUseCase(
                email = email,
                password = password
            ).collectLatest { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        _uiEvents.send(RegisterUiEvent.SetLoader(resource.loading))
                        _state.update {
                            it.copy(loader = resource.loading)
                        }
                    }

                    is Resource.Success -> {
                        _uiEvents.send(
                            RegisterUiEvent.NavigateToLogin(
                                email = email,
                                password = password
                            )
                        )

                        _state.update {
                            it.copy(registerSession = resource.data.toUi())
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(error = resource.errorMessage)
                        }
                        _uiEvents.send(RegisterUiEvent.ShowApiError(resource.errorMessage))
                    }
                }
            }
        }
    }
}