package com.example.baseproject.user

import com.example.baseproject.BaseFragment
import com.example.baseproject.databinding.FragmentLoginBinding
import com.example.baseproject.utils.makeVisibilityToggle


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun listeners() {
        passwordVisibilityToggle()
    }

    private fun passwordVisibilityToggle() {
        binding.btnVisibility.makeVisibilityToggle(editText = binding.etPassword)
    }
}