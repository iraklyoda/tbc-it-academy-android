package com.example.tricholog.ui.landing.pager

import androidx.navigation.fragment.findNavController
import com.example.tricholog.BaseFragment
import com.example.tricholog.databinding.FragmentAuthBinding
import com.example.tricholog.ui.landing.WelcomeFragmentDirections


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