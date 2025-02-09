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
import com.example.baseproject.databinding.FragmentRegisterBinding
import com.example.baseproject.data.remote.dto.ProfileDto
import com.example.baseproject.presentation.authentication.AuthState
import com.example.baseproject.utils.getString
import com.example.baseproject.utils.isEmail
import com.example.baseproject.utils.makeVisibilityToggle
import com.example.baseproject.utils.setLoaderState
import com.example.baseproject.utils.showErrorToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val registerViewModel: RegisterViewModel by viewModels()

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
                    registerViewModel.registerUser(
                        ProfileDto(
                            email = etEmail.getString(),
                            password = etPassword.getString()
                        )
                    )
                }
            }
        }
    }

    private fun observeRegistrationState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                var previousState: AuthState? = null

                registerViewModel.registerStateFlow.collectLatest { state ->
                    previousState?.let { previous ->
                        if (previous.loader != state.loader) {
                            binding.pbRegister.setLoaderState(
                                loading = state.loader,
                                actionBtn = binding.btnRegister
                            )
                        }

                        if (previous.userInfo != state.userInfo) {
                            setFragmentResult(
                                "credentials", bundleOf(
                                    "email" to state.userInfo?.email,
                                    "password" to state.userInfo?.password
                                )
                            )
                            findNavController().navigateUp()
                        }

                        if (previous.error != state.error) {
                            requireContext().showErrorToast(state.error.toString())
                        }

                    }
                    previousState = state
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

