package com.example.baseproject.user.controllers

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.BaseFragment
import com.example.baseproject.databinding.FragmentHomeBinding
import com.example.baseproject.user.HomeViewModel
import com.example.baseproject.user.UsersAdapter
import com.example.baseproject.user.UsersState
import com.example.baseproject.utils.showErrorToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val homeViewModel: HomeViewModel by viewModels()

    private val usersAdapter by lazy {
        UsersAdapter()
    }

    override fun start() {
        homeViewModel.fetchUsersInfo()
        setUsersAdapter()
    }

    override fun listeners() {
        goToProfilePage()
        observeUsersState()
    }

    private fun setUsersAdapter() {
        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsers.adapter = usersAdapter
    }

    private fun observeUsersState() {
        viewLifecycleOwner.lifecycleScope.launch {
            var previousState: UsersState? = null

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.usersStateFlow.collectLatest { state ->
                    previousState?.let { previous ->
                        if (previous.loader != state.loader) {
                            setLoader(state.loader)
                        }

                        if (previous.usersData != state.usersData) {
                            usersAdapter.submitList(state.usersData.toList())
                        }

                        if (previous.error != state.error) {
                            requireContext().showErrorToast(state.error.toString())
                        }
                    }
                    previousState = state
                }
            }
        }
    }

    private fun goToProfilePage() {
        binding.btnProfile.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            findNavController().navigate(action)
        }
    }

    private fun setLoader(loading: Boolean) {
        binding.apply {
            if (loading) {
                pb.visibility = View.VISIBLE
            } else {
                pb.visibility = View.GONE
            }
        }
    }
}
