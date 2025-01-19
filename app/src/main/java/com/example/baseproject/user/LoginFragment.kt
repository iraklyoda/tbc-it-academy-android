package com.example.baseproject.user

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentLoginBinding
import com.example.baseproject.utils.getString
import com.example.baseproject.utils.makeVisibilityToggle


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: UserViewModel by activityViewModels()

    override fun listeners() {
        observeErrors()
        observeUser()
        passwordVisibilityToggle()
        login()
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
                    "User ${user.username} logged in successfully",
                    Toast.LENGTH_SHORT
                )
                    .show()
                navigateToHello()
            }
        }
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            viewModel.loginUser(
                username = binding.etUsername.getString(),
                password = binding.etPassword.getString()
            )
        }
    }

    private fun navigateToHello() {
        val navOptions = NavOptions.Builder().setPopUpTo(R.id.firstFragment, true)
            .setLaunchSingleTop(true)
            .build()
        findNavController().navigate(R.id.action_loginFragment_to_helloFragment, null, navOptions)
    }

    private fun passwordVisibilityToggle() {
        binding.btnVisibility.makeVisibilityToggle(editText = binding.etPassword)
    }
}