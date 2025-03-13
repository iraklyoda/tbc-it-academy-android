package com.example.baseproject.ui.authentication.register

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.baseproject.databinding.FragmentRegisterBinding
import com.example.baseproject.ui.BaseFragment
import com.example.baseproject.ui.utils.AuthFieldErrorMapper
import com.example.baseproject.ui.utils.collect
import com.example.baseproject.ui.utils.collectLatest
import com.example.baseproject.utils.makeVisibilityToggle
import com.example.baseproject.utils.onTextChanged
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
        observeUiState()
        observeRegisterEvents()
    }

    private fun observeUiState() {
        collectLatest(flow = registerViewModel.uiState) { state ->
            binding.apply {
                pbRegister.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                etEmail.error =
                    AuthFieldErrorMapper.mapToString(state.emailError)?.let { getString(it) }

                etPassword.error =
                    AuthFieldErrorMapper.mapToString(state.passwordError)?.let { getString(it) }

                etRepeatPassword.error =
                    AuthFieldErrorMapper.mapToString(state.repeatedPasswordError)
                        ?.let { getString(it) }

                btnRegister.isEnabled = state.isSignUpBtnEnabled
            }
        }
    }

    private fun observeRegisterEvents() {
        collect(flow = registerViewModel.registerEvents) { event ->
            when (event) {
                is RegisterEvent.RegisterSuccess -> onRegisterSuccess(
                    email = event.email,
                    password = event.password
                )

                is RegisterEvent.RegisterError -> event.message?.let {
                    Log.d("RegisterNetworkError", "Log $it")
                    requireContext().showErrorToast(
                        it
                    )
                }
            }
        }
    }

    private fun setUpInputEvents() {
        binding.apply {
            etEmail.onTextChanged { email ->
                registerViewModel.handleEvent(RegisterUiEvents.EmailChanged(email))
            }
            etPassword.onTextChanged { password ->
                registerViewModel.handleEvent(RegisterUiEvents.PasswordChanged(password))
            }
            etRepeatPassword.onTextChanged { repeatedPassword ->
                registerViewModel.handleEvent(
                    RegisterUiEvents.RepeatedPasswordChanged(
                        repeatedPassword = repeatedPassword
                    )
                )
            }
        }
    }

    private fun registerBtnListener() {
        binding.btnRegister.setOnClickListener {
            registerViewModel.handleEvent(RegisterUiEvents.Submit)
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

