package com.example.baseproject.ui.authentication.register

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.baseproject.databinding.FragmentRegisterBinding
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.ui.BaseFragment
import com.example.baseproject.ui.utils.FieldErrorMapper
import com.example.baseproject.ui.utils.collect
import com.example.baseproject.ui.utils.collectLatest
import com.example.baseproject.utils.makeVisibilityToggle
import com.example.baseproject.utils.onTextChanged
import com.example.baseproject.utils.setLoaderState
import com.example.baseproject.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun start() {
        setPasswordVisibilityToggle()
        setUpInputEvents()
    }

    override fun listeners() {
        registerBtnListener()
    }

    override fun observers() {
        observeRegistrationState()
        observeValidationEvents()
        observeFormValidationErrors()
    }

    private fun setUpInputEvents() {
        binding.apply {
            etEmail.onTextChanged { email ->
                registerViewModel.handleEvent(RegisterFormEvent.EmailChanged(email))
            }
            etPassword.onTextChanged { password ->
                registerViewModel.handleEvent(RegisterFormEvent.PasswordChanged(password))
            }
            etRepeatPassword.onTextChanged { repeatedPassword ->
                registerViewModel.handleEvent(
                    RegisterFormEvent.RepeatedPasswordChanged(
                        repeatedPassword
                    )
                )
            }
        }
    }

    private fun observeFormValidationErrors() {
        collect(flow = registerViewModel.registerFormState) { state ->
            binding.apply {
                etEmail.error =
                    FieldErrorMapper.mapToString(state.emailError)?.let { getString(it) }
                etPassword.error =
                    FieldErrorMapper.mapToString(state.passwordError)?.let { getString(it) }
                etRepeatPassword.error =
                    FieldErrorMapper.mapToString(state.repeatedPasswordError)
                        ?.let { getString(it) }
            }
        }
    }

    private fun observeValidationEvents() {
        collect(flow = registerViewModel.validationEvents) { event ->
            when (event) {
                is RegisterViewModel.ValidationEvent.Success -> {
                    registerViewModel.register()
                }
            }
        }
    }

    private fun registerBtnListener() {
        binding.apply {
            btnRegister.setOnClickListener {
                registerViewModel.handleEvent(RegisterFormEvent.Submit)
            }
        }
    }

    private fun observeRegistrationState() {
        collectLatest(flow = registerViewModel.registerStateFlow) { resource ->
            when (resource) {
                is Resource.Loading -> binding.pbRegister.setLoaderState(loading = true)

                is Resource.Success -> {
                    binding.pbRegister.setLoaderState(
                        loading = false,
                    )
                    resource.data?.let {
                        setFragmentResult(
                            "credentials", bundleOf(
                                "email" to resource.data.email,
                                "password" to resource.data.password
                            )
                        )
                        findNavController().navigateUp()
                    }
                }

                is Resource.Error -> {
                    binding.pbRegister.setLoaderState(false)
                    requireContext().showErrorToast(resource.errorMessage)
                }
            }
        }
    }

    private fun setPasswordVisibilityToggle() {
        binding.btnVisibility.makeVisibilityToggle(editText = binding.etPassword)
        binding.btnRepeatVisibility.makeVisibilityToggle(editText = binding.etRepeatPassword)
    }
}

