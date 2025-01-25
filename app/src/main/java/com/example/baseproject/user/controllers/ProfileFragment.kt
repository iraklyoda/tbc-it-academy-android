package com.example.baseproject.user.controllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.data.AuthPreferencesRepository
import com.example.baseproject.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
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

                val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        }
    }
}