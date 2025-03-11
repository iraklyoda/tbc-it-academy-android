package com.example.baseproject.ui.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.model.ProfileSession
import com.example.baseproject.domain.preferences.AppPreferenceKeys
import com.example.baseproject.domain.use_case.auth.LogInUserUseCase
import com.example.baseproject.domain.use_case.preferences.SavePreferenceValueUseCase
import com.example.baseproject.domain.use_case.validation.ValidateEmailUseCase
import com.example.baseproject.domain.use_case.validation.ValidatePasswordUseCase
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
class LoginViewModel @Inject constructor(
    private val logInUserUseCase: LogInUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val savePreferenceValueUseCase: SavePreferenceValueUseCase
) : ViewModel() {

    private val _loginFormState = MutableStateFlow(LoginFormState())
    val loginFormState: StateFlow<LoginFormState> get() = _loginFormState

    private val _loginStateFlow: MutableStateFlow<Resource<ProfileSession?>> =
        MutableStateFlow(Resource.Success(null))
    val loginStateFlow get() = _loginStateFlow

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun handleEvent(event: LoginFormEvent) {
        _loginFormState.update {
            when(event) {
                is LoginFormEvent.EmailChanged -> it.copy(email = event.email)
                is LoginFormEvent.PasswordChanged -> it.copy(password = event.password)
                is LoginFormEvent.RememberMeChanged -> it.copy(rememberMe = event.rememberMe)
                is LoginFormEvent.Submit -> {
                    submitLoginData()
                    it
                }
            }
        }
    }

    private fun submitLoginData() {
        val formState = loginFormState.value

        val emailResult = validateEmailUseCase(formState.email)
        val passwordResult = validatePasswordUseCase(formState.password)

        _loginFormState.update {
            it.copy(
                emailError = emailResult.error,
                passwordError = passwordResult.error,
            )
        }

        if (listOf(
                emailResult,
                passwordResult,
            ).any { !it.successful }
        ) return

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    fun login() {
        val email = loginFormState.value.email
        val password = loginFormState.value.password
        val rememberMe = loginFormState.value.rememberMe

        viewModelScope.launch {
            logInUserUseCase(
                email = email,
                password = password,
                rememberMe = rememberMe
            ).collectLatest { resource ->
                _loginStateFlow.value = resource
            }
        }
    }

    fun saveAuthPreferences(token: String, email: String) {
        viewModelScope.launch {
            savePreferenceValueUseCase(key = AppPreferenceKeys.TOKEN_KEY, value = token)
            savePreferenceValueUseCase(key = AppPreferenceKeys.EMAIL_KEY, value = email)
        }
    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }
}