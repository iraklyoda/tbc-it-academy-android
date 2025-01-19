package com.example.baseproject.user

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentRegisterBinding
import com.example.baseproject.utils.getString
import com.example.baseproject.utils.isEmail
import com.example.baseproject.utils.makeVisibilityToggle

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: UserViewModel by activityViewModels()

    override fun listeners() {
        passwordVisibilityToggle()
        observeErrors()
        observeUser()
        registerUser()
    }

    private fun registerUser() {
        with(binding) {
            btnRegister.setOnClickListener {
                if (validateForm()) {
                    viewModel.registerUser(
                        UserDto(
                            email = etEmail.getString(),
                            username = etUsername.getString(),
                            password = etPassword.getString()
                        )
                    )
                }
            }
        }
    }

    private fun observeErrors() {
        viewModel.error.observe(this, Observer { value ->
            value?.let {
                val error = when (value) {
                    UserErrors.USER_ALREADY_EXISTS -> getString(R.string.user_with_similar_email_already_exists)
                    UserErrors.WRONG_CREDENTIALS -> getString(R.string.username_or_password_is_incorrect)
                }
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                viewModel.clearError()
            }
        })
    }

    private fun observeUser() {
        viewModel.user.observe(this) { user ->
            user?.let {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.user_created_successfully), Toast.LENGTH_SHORT
                )
                    .show()
                navigateToHello()
            }
        }
    }

    private fun navigateToHello() {
        val navOptions = NavOptions.Builder().setPopUpTo(R.id.firstFragment, true)
            .setLaunchSingleTop(true)
            .build()
        findNavController().navigate(
            R.id.action_registerFragment_to_helloFragment,
            null,
            navOptions
        )
    }


    private fun passwordVisibilityToggle() {
        binding.btnVisibility.makeVisibilityToggle(editText = binding.etPassword)
    }

    private fun validateForm(): Boolean {
        with(binding) {
            if (etEmail.getString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.email_is_required), Toast.LENGTH_SHORT
                ).show()
                return false
            } else if (!etEmail.getString().isEmail()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_enter_valid_email), Toast.LENGTH_SHORT
                )
                    .show()
                return false
            } else if (etEmail.getString().lowercase() != "eve.holt@reqres.in") {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.you_can_only_register_with_eve_holt_reqres_in_mail),
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }

            if (etUsername.getString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.username_is_required), Toast.LENGTH_SHORT
                ).show()
                return false
            }

            if (etPassword.getString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.password_is_required), Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
        return true
    }
}

