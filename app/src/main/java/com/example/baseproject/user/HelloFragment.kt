package com.example.baseproject.user

import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentHelloBinding

class HelloFragment : BaseFragment<FragmentHelloBinding>(FragmentHelloBinding::inflate) {
    private val viewModel: UserViewModel by activityViewModels()

    override fun start() {
        binding.textUsername.text = viewModel.user.value?.username

    }

    override fun listeners() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()

            val navOptions = NavOptions.Builder().setPopUpTo(R.id.nav_graph, true)
                .build()
            findNavController().navigate(
                R.id.action_helloFragment_to_firstFragment, null,
                navOptions
            )
        }
    }

}