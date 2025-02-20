package com.example.baseproject.ui.authentication.register

import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentRegisterBinding
import com.example.baseproject.utils.clearError
import com.example.baseproject.utils.getString
import com.example.baseproject.utils.isEmail
import com.example.baseproject.utils.showToast


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    override fun start() {

    }

    override fun listeners() {
        onRegister()
    }

    private fun onRegister() {
        binding.btnRegister.setOnClickListener {
            if (validateForm()) {
                requireContext().showToast("Validated!")
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        with(binding) {
            tlUsername.clearError()
            tlEmail.clearError()
            tlPassword.clearError()
            tlPasswordRepeat.clearError()

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
}