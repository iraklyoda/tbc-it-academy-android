package com.iraklyoda.userssocialapp.presentation.screen.authentication.login

import com.iraklyoda.userssocialapp.MainDispatcherRule
import com.iraklyoda.userssocialapp.domain.common.AuthFieldErrorType
import com.iraklyoda.userssocialapp.domain.common.Resource
import com.iraklyoda.userssocialapp.domain.model.LoginSession
import com.iraklyoda.userssocialapp.domain.use_case.auth.LogInUserUseCase
import com.iraklyoda.userssocialapp.domain.use_case.validation.ValidateEmailUseCase
import com.iraklyoda.userssocialapp.domain.use_case.validation.ValidatePasswordUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoginViewModel

    private val mockLogInUserUseCase = mockk<LogInUserUseCase>()
    private val mockValidateEmailUseCase = mockk<ValidateEmailUseCase>()
    private val mockValidatePasswordUseCase = mockk<ValidatePasswordUseCase>()

    @Before
    fun setUp() {
        viewModel = LoginViewModel(
            logInUserUseCase = mockLogInUserUseCase,
            validateEmailUseCase = mockValidateEmailUseCase,
            validatePasswordUseCase = mockValidatePasswordUseCase
        )
    }

    @Test
    fun `onEmailChanged updates email in state`() {
        viewModel.onEvent(LoginEvent.EmailChanged("test@mail.com"))
        assert(viewModel.uiState.email == "test@mail.com")
    }

    @Test
    fun `onPasswordChanged updates password in uiState`() {
        viewModel.onEvent(LoginEvent.PasswordChanged("123456"))
        assert(viewModel.uiState.password == "123456")
    }

    @Test
    fun `toggle password visibility flips the flag`() {
        val initial = viewModel.uiState.passwordVisible
        viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
        assert(viewModel.uiState.passwordVisible != initial)
    }

    @Test
    fun `toggle remember me flips the flag`() {
        val initial = viewModel.uiState.rememberMe
        viewModel.onEvent(LoginEvent.ToggleRememberMe)
        assert(viewModel.uiState.rememberMe != initial)
    }

    @Test
    fun `submit invalid form sets formBeenSubmitted to true`() {
        every { mockValidateEmailUseCase(any()) } returns AuthFieldErrorType.InvalidFormat
        every { mockValidatePasswordUseCase(any()) } returns AuthFieldErrorType.EMPTY

        viewModel.onEvent(LoginEvent.Submit)

        assert(viewModel.state.formBeenSubmitted)
        assert(viewModel.state.emailErrorResource != null)
        assert(viewModel.state.passwordErrorResource != null)
    }

    @Test
    fun `successful login triggers NavigateToHome side effect`() = runTest {
        every { mockValidateEmailUseCase(any()) } returns null
        every { mockValidatePasswordUseCase(any()) } returns null
        coEvery {
            mockLogInUserUseCase(any(), any(), any())
        } returns flowOf(Resource.Success(LoginSession(token = "testToken")))

        viewModel.onEvent(LoginEvent.EmailChanged("test@mail.com"))
        viewModel.onEvent(LoginEvent.PasswordChanged("password123"))
        viewModel.onEvent(LoginEvent.Submit)

        val effect = viewModel.sideEffect.first()
        assert(effect is LoginSideEffect.NavigateToHome)
    }

    @Test
    fun `login error sets error in state and triggers side effect`() = runTest {
        every { mockValidateEmailUseCase(any()) } returns null
        every { mockValidatePasswordUseCase(any()) } returns null
        coEvery {
            mockLogInUserUseCase(any(), any(), any())
        } returns flowOf(Resource.Error("Login failed"))

        viewModel.onEvent(LoginEvent.EmailChanged("test@mail.com"))
        viewModel.onEvent(LoginEvent.PasswordChanged("password123"))
        viewModel.onEvent(LoginEvent.Submit)

        assert(viewModel.state.apiError == "Login failed")
        val effect = viewModel.sideEffect.first()
        assert(effect is LoginSideEffect.ShowAuthError)
    }

}