package com.iraklyoda.userssocialapp.presentation.screen.authentication.register

import com.iraklyoda.userssocialapp.MainDispatcherRule
import com.iraklyoda.userssocialapp.domain.common.AuthFieldErrorType
import com.iraklyoda.userssocialapp.domain.common.Resource
import com.iraklyoda.userssocialapp.domain.model.RegisterSession
import com.iraklyoda.userssocialapp.domain.use_case.auth.SignUpUserUseCase
import com.iraklyoda.userssocialapp.domain.use_case.validation.ValidateEmailUseCase
import com.iraklyoda.userssocialapp.domain.use_case.validation.ValidatePasswordUseCase
import com.iraklyoda.userssocialapp.domain.use_case.validation.ValidateRepeatedPasswordUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: RegisterViewModel

    private val mockSignUpUserUseCase = mockk<SignUpUserUseCase>()
    private val mockValidateEmailUseCase = mockk<ValidateEmailUseCase>()
    private val mockValidatePasswordUseCase = mockk<ValidatePasswordUseCase>()
    private val mockValidateRepeatedPasswordUseCase = mockk<ValidateRepeatedPasswordUseCase>()

    @Before
    fun setUp() {
        viewModel = RegisterViewModel(
            signUpUserUseCase = mockSignUpUserUseCase,
            validateEmailUseCase = mockValidateEmailUseCase,
            validatePasswordUseCase = mockValidatePasswordUseCase,
            validateRepeatedPasswordUseCase = mockValidateRepeatedPasswordUseCase
        )
    }

    @Test
    fun `email change updates ui state`() {
        viewModel.onEvent(RegisterEvent.EmailChanged("email@test.com"))
        assert(viewModel.uiState.email == "email@test.com")
    }

    @Test
    fun `password change updates ui state`() {
        viewModel.onEvent(RegisterEvent.PasswordChanged("mypassword", repeatedPassword = "12345678"))
        assert(viewModel.uiState.password == "mypassword")
    }

    @Test
    fun `repeated password change updates ui state`() {
        viewModel.onEvent(RegisterEvent.RepeatedPasswordChanged("1234567", password = "12345678"))
        assert(viewModel.uiState.repeatedPassword == "1234567")
    }

    @Test
    fun `toggle password visibility updates ui state`() {
        val initial = viewModel.uiState.passwordVisible
        viewModel.onEvent(RegisterEvent.TogglePasswordVisibility)
        assert(viewModel.uiState.passwordVisible != initial)
    }

    @Test
    fun `toggle repeated password visibility updates ui state`() {
        val initial = viewModel.uiState.repeatedPasswordVisible
        viewModel.onEvent(RegisterEvent.ToggleRepeatPasswordVisibility)
        assert(viewModel.uiState.repeatedPasswordVisible != initial)
    }

    @Test
    fun `submit with invalid fields sets form as submitted and shows errors`() {
        every { mockValidateEmailUseCase(any()) } returns AuthFieldErrorType.InvalidFormat
        every { mockValidatePasswordUseCase(any()) } returns AuthFieldErrorType.EMPTY
        every { mockValidateRepeatedPasswordUseCase(any(), any()) } returns AuthFieldErrorType.PasswordsDoNotMatch

        viewModel.onEvent(RegisterEvent.Submit)

        assert(viewModel.state.formBeenSubmitted)
        assert(viewModel.state.emailErrorResource != null)
        assert(viewModel.state.passwordErrorResource != null)
        assert(viewModel.state.repeatedPasswordErrorResource != null)
    }

    @Test
    fun `successful registration emits NavigateToLogin side effect`() = runTest {
        every { mockValidateEmailUseCase(any()) } returns null
        every { mockValidatePasswordUseCase(any()) } returns null
        every { mockValidateRepeatedPasswordUseCase(any(), any()) } returns null

        coEvery { mockSignUpUserUseCase(any(), any()) } returns flowOf(Resource.Success(
            RegisterSession(id = 1, token = "123Test321")
        ))

        viewModel.onEvent(RegisterEvent.EmailChanged("email@test.com"))
        viewModel.onEvent(RegisterEvent.PasswordChanged("12345678", repeatedPassword = "12345678"))
        viewModel.onEvent(RegisterEvent.RepeatedPasswordChanged("12345678", password = "12345678"))
        viewModel.onEvent(RegisterEvent.Submit)

        val effect = viewModel.sideEffect.first()
        assert(effect is RegisterSideEffect.NavigateToLogin)
    }

    @Test
    fun `failed registration emits error and sets apiError`() = runTest {
        every { mockValidateEmailUseCase(any()) } returns null
        every { mockValidatePasswordUseCase(any()) } returns null
        every { mockValidateRepeatedPasswordUseCase(any(), any()) } returns null

        coEvery { mockSignUpUserUseCase(any(), any()) } returns flowOf(Resource.Error("Email taken"))

        viewModel.onEvent(RegisterEvent.EmailChanged("email@test.com"))
        viewModel.onEvent(RegisterEvent.PasswordChanged("12345678", repeatedPassword = "12345678"))
        viewModel.onEvent(RegisterEvent.RepeatedPasswordChanged("12345678", password = "12345678"))
        viewModel.onEvent(RegisterEvent.Submit)

        val effect = viewModel.sideEffect.first()
        assert(effect is RegisterSideEffect.ShowApiError)
        assert(viewModel.state.apiError == "Email taken")
    }
}