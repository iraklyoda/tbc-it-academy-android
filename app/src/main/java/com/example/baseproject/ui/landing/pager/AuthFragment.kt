package com.example.baseproject.ui.landing.pager

import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.databinding.FragmentAuthBinding
import com.example.baseproject.ui.landing.WelcomeFragmentDirections
import com.example.baseproject.utils.fadeIn


class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {
    override fun start() {
    }

    override fun listeners() {
        onLogin()
        onRegister()
    }

    private fun onLogin() {
        val action = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(action)
        }
    }

    private fun onRegister() {
        val action = WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment()
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(action)
        }
    }
}