package com.example.baseproject.presentation.authentication.screen.register

import android.view.View
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.baseproject.databinding.FragmentRegisterBinding
import com.example.baseproject.domain.common.AuthFieldErrorType
import com.example.baseproject.presentation.BaseFragment
import com.example.baseproject.presentation.utils.AuthFieldErrorMapper
import com.example.baseproject.presentation.utils.collect
import com.example.baseproject.presentation.utils.getString
import com.example.baseproject.presentation.utils.makeVisibilityToggle
import com.example.baseproject.presentation.utils.onTextChanged
import com.example.baseproject.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun start() {
        setUpInputEvents()
        setPasswordVisibilityToggle()

    }

    override fun listeners() {
        registerBtnListener()
    }

    override fun observers() {
        setupEventObservers()
    }

    private fun setupEventObservers() {
        collect(flow = registerViewModel.uiEvents) { event ->
            when (event) {
                is RegisterUiEvent.SetLoader -> setLoader(isLoading = event.isLoading)

                is RegisterUiEvent.SetEmailInputError -> setInputError(
                    inputField = binding.etEmail,
                    error = event.error
                )

                is RegisterUiEvent.SetPasswordInputError -> setInputError(
                    inputField = binding.etPassword,
                    error = event.error
                )

                is RegisterUiEvent.SetRepeatedPasswordInputError -> setInputError(
                    inputField = binding.etRepeatPassword,
                    error = event.error
                )

                is RegisterUiEvent.SetRegisterBtnStatus -> binding.btnRegister.isEnabled =
                    event.isEnabled

                is RegisterUiEvent.NavigateToLogin -> onRegisterSuccess(
                    email = event.email,
                    password = event.password
                )

                is RegisterUiEvent.ShowApiError -> event.message?.let {
                    requireContext().showErrorToast(
                        it
                    )
                }
            }
        }
    }

    private fun setLoader(isLoading: Boolean) {
        binding.pbRegister.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setInputError(
        inputField: EditText,
        error: AuthFieldErrorType?
    ) {
        inputField.error = AuthFieldErrorMapper.mapToString(error)?.let { errorMessageId ->
            getString(errorMessageId)
        }
    }

    private fun setUpInputEvents() {
        binding.apply {
            // Email
            etEmail.onTextChanged { email ->
                registerViewModel.onEvent(
                    RegisterEvent.EmailChanged(
                        email = email
                    )
                )
            }

            // Password
            etPassword.onTextChanged { password ->
                registerViewModel.onEvent(
                    RegisterEvent.PasswordChanged(
                        password = password,
                        repeatedPassword = etRepeatPassword.getString()
                    )
                )
            }

            // Repeated password
            etRepeatPassword.onTextChanged { repeatedPassword ->
                registerViewModel.onEvent(
                    RegisterEvent.RepeatedPasswordChanged(
                        repeatedPassword = repeatedPassword,
                        password = etPassword.getString()
                    )
                )
            }
        }
    }

    private fun registerBtnListener() {
        binding.apply {
            btnRegister.setOnClickListener {
                registerViewModel.onEvent(
                    RegisterEvent.Submit(
                        email = etEmail.getString(),
                        password = etPassword.getString(),
                        repeatedPassword = etRepeatPassword.getString()
                    )
                )
            }
        }
    }

    private fun onRegisterSuccess(email: String, password: String) {
        setFragmentResult(
            "credentials", bundleOf(
                "email" to email,
                "password" to password
            )
        )
        findNavController().navigateUp()
    }

    private fun setPasswordVisibilityToggle() {
        binding.btnVisibility.makeVisibilityToggle(editText = binding.etPassword)
        binding.btnRepeatVisibility.makeVisibilityToggle(editText = binding.etRepeatPassword)
    }
}

