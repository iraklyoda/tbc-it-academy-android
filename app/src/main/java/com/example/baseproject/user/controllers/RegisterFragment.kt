package com.example.baseproject.user.controllers

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentRegisterBinding
import com.example.baseproject.user.RegisterViewModel
import com.example.baseproject.user.ProfileDto
import com.example.baseproject.utils.getString
import com.example.baseproject.utils.isEmail
import com.example.baseproject.utils.makeVisibilityToggle
import com.example.baseproject.utils.showErrorToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun listeners() {
        passwordVisibilityToggle()
        register()
        observeRegistration()
        observeLoadingData()
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
                        ),
                        onFailed = { error ->
                            withContext(Dispatchers.Main) {
                                requireContext().showErrorToast(error)
                            }
                        }
                    )
                }
            }
        }
    }

    private fun observeLoadingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            registerViewModel.isLoading.collect() { loading ->
                binding.apply {
                    if (loading) {
                        pb.visibility = View.VISIBLE
                        btnRegister.isEnabled = false
                    } else {
                        pb.visibility = View.GONE
                        btnRegister.isEnabled = true
                    }
                }
            }
        }
    }

    private fun observeRegistration() {
        viewLifecycleOwner.lifecycleScope.launch {
            registerViewModel.registrationSuccess.collect() { success ->
                if (success) {
                    setFragmentResult("credentials", bundleOf(
                        "email" to binding.etEmail.getString(),
                        "password" to binding.etPassword.getString()
                    ))

                    findNavController().navigateUp()
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

