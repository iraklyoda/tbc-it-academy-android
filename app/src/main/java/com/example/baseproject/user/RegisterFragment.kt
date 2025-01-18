package com.example.baseproject.user

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentRegisterBinding
import com.example.baseproject.utils.getString
import com.example.baseproject.utils.isEmail
import com.example.baseproject.utils.makeVisibilityToggle

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: UserViewModel by viewModels()

    override fun start() {
        viewModel.error.observe(this, Observer { value ->
            Toast.makeText(requireContext(), "$value", Toast.LENGTH_SHORT).show()
        })

        viewModel.user.observe(this, Observer {
            Toast.makeText(requireContext(), "User Created successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_registerFragment_to_firstFragment)
        })
    }

    override fun listeners() {
        passwordVisibilityToggle()

        binding.btnRegister.setOnClickListener {
            if (validateForm()) {
                viewModel.registerUser(
                    UserDto(
                        email = binding.etEmail.text.toString().trim(),
                        password = binding.etPassword.text.toString().trim()
                    )
                )
            }
        }
    }

    private fun passwordVisibilityToggle() {
        binding.btnVisibility.makeVisibilityToggle(editText = binding.etPassword)
    }

    private fun validateForm(): Boolean {
        with(binding) {
            if (etEmail.getString().isEmpty()) {
                Toast.makeText(requireContext(), "Email is required", Toast.LENGTH_SHORT).show()
                return false
            } else if (!etEmail.getString().isEmail()) {
                Toast.makeText(requireContext(), "Please enter valid email", Toast.LENGTH_SHORT).show()
                return false
            }

            if (etUsername.getString().isEmpty()) {
                Toast.makeText(requireContext(), "Username is required", Toast.LENGTH_SHORT).show()
                return false
            }

            if (etPassword.getString().isEmpty()) {
                Toast.makeText(requireContext(), "Password is required", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }
}

