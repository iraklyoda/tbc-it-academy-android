package com.example.baseproject.user.controllers

import android.content.SharedPreferences
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.data.SessionRepository
import com.example.baseproject.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private lateinit var sharedPreferences: SharedPreferences

    private val navController by lazy {
        findNavController()
    }

    private lateinit var sessionRepository: SessionRepository


    override fun start() {
        sessionRepository = SessionRepository(requireContext())
        binding.textEmail.text = sessionRepository.getEmail()

        binding.btnLogout.setOnClickListener {
            sessionRepository.clearAttributes()
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, true)
                .build()
            sessionRepository.clearAttributes()
            findNavController().navigate(action, navOptions)
        }
    }
}
