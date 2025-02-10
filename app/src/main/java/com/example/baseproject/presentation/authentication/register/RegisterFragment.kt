package com.example.baseproject.presentation.authentication.register

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.common.Resource
import com.example.baseproject.data.local.AuthPreferencesRepository
import com.example.baseproject.data.remote.AuthRepository
import com.example.baseproject.data.remote.api.RetrofitClient
import com.example.baseproject.databinding.FragmentRegisterBinding
import com.example.baseproject.utils.getString
import com.example.baseproject.utils.isEmail
import com.example.baseproject.utils.makeVisibilityToggle
import com.example.baseproject.utils.setLoaderState
import com.example.baseproject.utils.showErrorToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val authRepository: AuthRepository by lazy {
        AuthRepository(
            apiService = RetrofitClient.authService,
            authPreferencesRepository = AuthPreferencesRepository(requireContext().applicationContext)
        )
    }

    private val registerViewModel: RegisterViewModel by viewModels() {
        RegisterViewModel.Factory(authRepository)
    }

    override fun listeners() {
        passwordVisibilityToggle()
        register()
        observeRegistrationState()
    }

    private fun passwordVisibilityToggle() {
        binding.btnVisibility.makeVisibilityToggle(editText = binding.etPassword)
        binding.btnRepeatVisibility.makeVisibilityToggle(editText = binding.etRepeatPassword)
    }

    private fun register() {
        binding.apply {
            btnRegister.setOnClickListener {
                if (validateForm()) {
                    registerViewModel.register(
                        email = etEmail.getString(),
                        password = etPassword.getString()
                    )
                }
            }
        }
    }

    private fun observeRegistrationState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                registerViewModel.registerStateFlow.collectLatest { resource ->

                    when (resource) {
                        is Resource.Loading -> {
                            binding.pbRegister.setLoaderState(
                                loading = resource.loading,
                                actionBtn = binding.btnRegister
                            )
                        }

                        is Resource.Success -> {
                            setFragmentResult(
                                "credentials", bundleOf(
                                    "email" to resource.data.email,
                                    "password" to resource.data.password
                                )
                            )
                            findNavController().navigateUp()
                        }

                        is Resource.Error -> {
                            requireContext().showErrorToast(resource.errorMessage)
                        }
                    }
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        with(binding) {
            if (!etEmail.getString().isEmail())
                return requireContext().showErrorToast(getString(R.string.please_enter_valid_email))

            if (etPassword.getString().isEmpty())
                return requireContext().showErrorToast(getString(R.string.password_is_required))

            if (etPassword.getString() != etRepeatPassword.getString())
                return requireContext().showErrorToast(getString(R.string.passwords_doesn_t_match))
        }
        return true
    }
}

