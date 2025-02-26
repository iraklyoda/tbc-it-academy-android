package com.example.tricholog.ui.authentication.register

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tricholog.BaseFragment
import com.example.tricholog.R
import com.example.tricholog.databinding.FragmentRegisterBinding
import com.example.tricholog.domain.error.AuthError
import com.example.tricholog.utils.clearError
import com.example.tricholog.utils.getString
import com.example.tricholog.utils.isEmail
import com.example.tricholog.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun start() {
    }

    override fun listeners() {
        onRegister()
        observeSignUp()
    }

    private fun observeSignUp() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.registerStateFlow.collectLatest { state ->
                    when (state) {
                        is RegisterUiState.Loading -> {
                            handleLoadingState(true)
                        }

                        is RegisterUiState.Success -> {
                            handleRegisterSuccess()
                        }

                        is RegisterUiState.Error -> {
                            handleAuthErrors(state.error)
                        }

                        is RegisterUiState.Idle -> {
                            handleLoadingState(false)
                        }
                    }
                }
            }
        }
    }

    private fun handleAuthErrors(error: AuthError) {
        handleLoadingState(false)

        when (error) {
            is AuthError.EmailAlreadyExists -> handleEmailExistsError()
            is AuthError.NetworkError -> requireContext().showToast(getString(R.string.network_issues))
            else -> requireContext().showToast(getString(R.string.unknown_error))
        }
    }

    private fun onRegister() {
        binding.btnRegister.setOnClickListener {
            if (validateForm()) {
                registerViewModel.signUp(
                    email = binding.etEmail.getString(),
                    password = binding.etPassword.getString()
                )
            }
        }
    }

    private fun handleRegisterSuccess() {
        handleLoadingState(false)

        val action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun handleLoadingState(isLoading: Boolean) {
        binding.pbSignUp.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnRegister.isActivated = !isLoading
    }


    private fun validateForm(): Boolean {
        var isValid: Boolean = true
        with(binding) {
            listOf(tlUsername, tlEmail, tlPassword, tlPasswordRepeat)
                .forEach { it.clearError() }

            if (etUsername.getString().isEmpty()) {
                isValid = false
                tlUsername.error = getString(R.string.username_is_empty)
            } else if (etUsername.getString().length < 4) {
                isValid = false
                tlUsername.error = getString(R.string.username_should_have_more_than_3_characters)
            }

            if (etEmail.getString().isEmpty()) {
                isValid = false
                tlEmail.error = getString(R.string.email_is_empty)
            } else if (!etEmail.getString().isEmail()) {
                isValid = false
                tlEmail.error = getString(R.string.please_enter_proper_email)
            }

            if (etPassword.getString().isEmpty()) {
                isValid = false
                tlPassword.error = getString(R.string.password_is_empty)
            } else if (etPassword.getString().length < 5) {
                isValid = false
                tlPassword.error = getString(R.string.password_should_have_more_than_4_characters)
            }

            if (etPasswordRepeat.getString().isEmpty()) {
                isValid = false
                tlPasswordRepeat.error = getString(R.string.field_is_empty)
            } else if (etPasswordRepeat.getString() != etPassword.getString()) {
                isValid = false
                tlPasswordRepeat.error = getString(R.string.password_do_not_match)
            }
        }
        return isValid
    }

    private fun handleEmailExistsError() {
        binding.tlEmail.error = getString(R.string.email_already_exists)
    }
}