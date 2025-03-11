package com.example.baseproject.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.baseproject.databinding.FragmentProfileBinding
import com.example.baseproject.ui.BaseFragment
import com.example.baseproject.ui.utils.viewLifecycleScope
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun start() {
        setEmail()
    }

    override fun listeners() {
        logoutBtnListener()
    }

    private fun setEmail() {
        viewLifecycleScope {
            binding.textEmail.text = profileViewModel.getEmail()
        }
    }

    private fun logoutBtnListener() {
        binding.btnLogout.setOnClickListener {
            handleLogout()
        }
    }

    private fun handleLogout() {
        viewLifecycleScope {
            profileViewModel.clearSession()
            val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
}