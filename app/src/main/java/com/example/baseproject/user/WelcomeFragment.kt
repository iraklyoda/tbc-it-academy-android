package com.example.baseproject.user

import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {
    private val navController by lazy {
        findNavController()
    }


    override fun start() {

    }

    override fun listeners() {
        with(binding) {
            btnRegister.setOnClickListener {
                navController.navigate(R.id.action_firstFragment_to_registerFragment)
            }

            btnLogin.setOnClickListener {
                navController.navigate(R.id.action_firstFragment_to_loginFragment)
            }
        }
    }
}
