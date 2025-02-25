package com.example.tricholog.ui.authentication.register

import android.util.Log.d
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tricholog.BaseFragment
import com.example.tricholog.R
import com.example.tricholog.databinding.FragmentRegisterBinding
import com.example.tricholog.utils.clearError
import com.example.tricholog.utils.getString
import com.example.tricholog.utils.isEmail
import com.example.tricholog.utils.showToast
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
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
                            binding.pbSignUp.visibility = View.VISIBLE
                            binding.btnRegister.isActivated = false
                        }

                        is RegisterUiState.Success -> {
                            binding.pbSignUp.visibility = View.GONE
                            binding.btnRegister.isActivated = true
                            d("RegisterStatus", "Success")
                            registerSuccess()
                        }

                        is RegisterUiState.Error -> {
                            binding.pbSignUp.visibility = View.GONE
                            binding.btnRegister.isActivated = true
                            d("RegisterStatus", "Error ${state.message}")
                            requireContext().showToast(handleFirebaseErrors(state.message))
                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun handleFirebaseErrors(e: Exception): String {
        return when (e) {
            is FirebaseAuthUserCollisionException -> getString(R.string.email_already_exists)
            is FirebaseNetworkException -> getString(R.string.network_issues)
            is FirebaseAuthWeakPasswordException -> getString(R.string.password_is_too_weak)
            else -> getString(R.string.unknown_error)
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

    private fun registerSuccess() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
        findNavController().navigate(action)
    }


    private fun validateForm(): Boolean {
        var isValid: Boolean = true
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