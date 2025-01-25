package com.example.baseproject.user.controllers

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.data.AuthPreferencesRepository
import com.example.baseproject.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private lateinit var authPreferencesRepository: AuthPreferencesRepository

    override fun start() {
        authPreferencesRepository = AuthPreferencesRepository(requireContext().applicationContext)

        setEmail()
    }

    override fun listeners() {
        logout()
    }

    private fun setEmail() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.textEmail.text = authPreferencesRepository.getEmail()
        }
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                authPreferencesRepository.clearAttributes()

                val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        }
    }
}
