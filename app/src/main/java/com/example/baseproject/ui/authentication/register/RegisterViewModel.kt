package com.example.baseproject.ui.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.use_case.auth.SignUpUserUseCase
import com.example.baseproject.domain.use_case.validation.ValidateEmailUseCase
import com.example.baseproject.domain.use_case.validation.ValidatePasswordUseCase
import com.example.baseproject.domain.use_case.validation.ValidateRepeatedPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
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

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> get() = _uiState

    private val _registerEvents = Channel<RegisterEvent>()
    val registerEvents = _registerEvents.receiveAsFlow()

    fun handleEvent(event: RegisterUiEvents) {
        val formBeenSubmitted = uiState.value.formBeenSubmitted

        when (event) {
            // Email Validation
            is RegisterUiEvents.EmailChanged -> {
                val emailError =
                    if (formBeenSubmitted) validateEmailUseCase(event.email).error else null

                _uiState.update {
                    it.copy(email = event.email, emailError = emailError)
                }
            }

            // Password Validation
            is RegisterUiEvents.PasswordChanged -> {
                val passwordError =
                    if (formBeenSubmitted) validatePasswordUseCase(event.password).error else null

                val repeatedPasswordError =
                    if (formBeenSubmitted) validateRepeatedPasswordUseCase(
                        password = event.password,
                        repeatedPassword = uiState.value.repeatedPassword
                    ).error else null

                _uiState.update {
                    it.copy(
                        password = event.password,
                        passwordError = passwordError,
                        repeatedPasswordError = repeatedPasswordError
                    )
                }
            }

            // Repeated Password Validation
            is RegisterUiEvents.RepeatedPasswordChanged -> {
                val repeatedPasswordError =
                    if (formBeenSubmitted) validateRepeatedPasswordUseCase(
                        password = uiState.value.password,
                        repeatedPassword = event.repeatedPassword
                    ).error else null

                _uiState.update {
                    it.copy(
                        repeatedPassword = event.repeatedPassword,
                        repeatedPasswordError = repeatedPasswordError
                    )
                }
            }

            is RegisterUiEvents.Submit -> {
                submitRegisterData()
            }
        }
    }


    private fun submitRegisterData() {
        if (validateForm())
            register(
                email = uiState.value.email,
                password = uiState.value.password
            )
        else
            _uiState.update { it.copy(formBeenSubmitted = true) }
    }

    private fun validateForm(): Boolean {
        val emailResult = validateEmailUseCase(uiState.value.email)
        val passwordResult = validatePasswordUseCase(uiState.value.password)
        val repeatedPasswordResult = validateRepeatedPasswordUseCase(
            password = uiState.value.password,
            repeatedPassword = uiState.value.repeatedPassword
        )

        _uiState.update {
            it.copy(
                emailError = emailResult.error,
                passwordError = passwordResult.error,
                repeatedPasswordError = repeatedPasswordResult.error
            )
        }

        return emailResult.successful && passwordResult.successful && repeatedPasswordResult.successful
    }

    private fun register(email: String, password: String) {
        viewModelScope.launch {
            signUpUserUseCase(
                email = email,
                password = password
            ).collectLatest { resource ->
                when (resource) {
                    is Resource.Loader -> _uiState.update { it.copy(isLoading = true) }

                    is Resource.Success -> {
                        _registerEvents.send(
                            RegisterEvent.RegisterSuccess(
                                email = uiState.value.email,
                                password = uiState.value.password
                            )
                        )

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                registerSession = resource.data
                            )
                        }
                    }

                    is Resource.Error -> _uiState.update {
                        _registerEvents.send(RegisterEvent.RegisterError(resource.errorMessage))
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }
}

sealed interface RegisterEvent {
    data class RegisterSuccess(val email: String, val password: String) : RegisterEvent
    data class RegisterError(val message: String?) : RegisterEvent
}