package com.example.tricholog.ui.authentication.login

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tricholog.BaseFragment
import com.example.tricholog.R
import com.example.tricholog.databinding.FragmentLoginBinding
import com.example.tricholog.domain.error.AuthError
import com.example.tricholog.utils.clearError
import com.example.tricholog.utils.getString
import com.example.tricholog.utils.isEmail
import com.example.tricholog.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun start() {
    }

    override fun listeners() {
        onLogin()
        handleLoginState()
    }

    private fun onLogin() {
        binding.btnLogin.setOnClickListener {
            if (validateForm()) {
                loginViewModel.signIn(
                    email = binding.etEmail.getString(),
                    password = binding.etPassword.getString()
                )
            }
        }
    }

    private fun handleLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginStateFlow.collectLatest { state ->
                    when (state) {
                        is LoginUiState.Loading -> {
                            handleLoadingState(true)
                        }

                        is LoginUiState.Success -> {
                            handleLoginSuccess()
                        }

                        is LoginUiState.Error -> {
                            handleAuthErrors(state.error)
                        }

                        is LoginUiState.Idle -> {

                        }
                    }
                }
            }
        }
    }

    private fun handleAuthErrors(error: AuthError) {
        handleLoadingState(false)
        when (error) {
            is AuthError.NetworkError -> requireContext().showToast(getString(R.string.network_issues))
            is AuthError.InvalidCredentials -> requireContext().showToast(getString(R.string.wrong_email_or_password))
            else -> requireContext().showToast(getString(R.string.unknown_error))
        }
    }

    private fun handleLoginSuccess() {
        handleLoadingState(false)

        val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
        findNavController().navigate(action)
    }

    private fun handleLoadingState(isLoading: Boolean) {
        binding.pbLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnLogin.isActivated = !isLoading
    }

    private fun validateForm(): Boolean {
        var isValid = true

        with(binding) {
            listOf(tlEmail, tlPassword)
                .forEach { it.clearError() }

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
        }

        return isValid
    }
}