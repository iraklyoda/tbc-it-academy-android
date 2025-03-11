package com.example.baseproject.ui.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.model.RegisterSession
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

    private val _registerFormState = MutableStateFlow(RegisterFormState())
    val registerFormState: StateFlow<RegisterFormState> get() = _registerFormState

    private val _registerStateFlow: MutableStateFlow<Resource<RegisterSession?>> =
        MutableStateFlow(Resource.Success(null))
    val registerStateFlow: StateFlow<Resource<RegisterSession?>> get() = _registerStateFlow


    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun handleEvent(event: RegisterFormEvent) {
        _registerFormState.update {
            when (event) {
                is RegisterFormEvent.EmailChanged -> it.copy(email = event.email)
                is RegisterFormEvent.PasswordChanged -> it.copy(password = event.password)
                is RegisterFormEvent.RepeatedPasswordChanged -> it.copy(repeatedPassword = event.repeatedPassword)
                is RegisterFormEvent.Submit -> {
                    submitRegisterData()
                    it
                }
            }
        }
    }

    private fun submitRegisterData() {
        val formState = registerFormState.value

        val emailResult = validateEmailUseCase(formState.email)
        val passwordResult = validatePasswordUseCase(formState.password)
        val repeatedPasswordResult = validateRepeatedPasswordUseCase(
            password = formState.password,
            repeatedPassword = formState.repeatedPassword
        )

        _registerFormState.update {
            it.copy(
                emailError = emailResult.error,
                passwordError = passwordResult.error,
                repeatedPasswordError = repeatedPasswordResult.error
            )
        }

        if (listOf(
                emailResult,
                passwordResult,
                repeatedPasswordResult
            ).any { !it.successful }
        ) return

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    fun register() {
        val email = registerFormState.value.email
        val password = registerFormState.value.password

        viewModelScope.launch {
            signUpUserUseCase(
                email = email,
                password = password
            ).collectLatest { resource ->
                _registerStateFlow.value = resource
            }
        }
    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }
}