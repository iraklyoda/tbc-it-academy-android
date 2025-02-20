package com.example.baseproject.ui.authentication.login

import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentLoginBinding
import com.example.baseproject.utils.clearError
import com.example.baseproject.utils.getString
import com.example.baseproject.utils.isEmail
import com.example.baseproject.utils.showToast


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun start() {
    }

    override fun listeners() {
        onLogin()
    }

    private fun onLogin() {
        binding.btnLogin.setOnClickListener {
            if (validateForm()) {
                requireContext().showToast("Validated")
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        with(binding) {
            tlEmail.clearError()
            tlPassword.clearError()

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