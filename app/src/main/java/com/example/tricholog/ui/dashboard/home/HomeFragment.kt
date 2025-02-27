package com.example.tricholog.ui.dashboard.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tricholog.BaseFragment
import com.example.tricholog.databinding.FragmentHomeBinding
import com.example.tricholog.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun start() {
        handleUser()
    }

    override fun listeners() {

    }

    private fun handleUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.currentUser.collectLatest { user: User? ->
                    if (user != null) {
                        Log.d("HomeFragment", "User: ${user.username}, Email: ${user.email}")
                        binding.tvUser.text = "User: ${user.username}, Email: ${user.email}"
                    } else {
                        Log.d("HomeFragment", "No user found")
                        // Handle the case where user is null
                    }
                }

            }

        }
    }
}
