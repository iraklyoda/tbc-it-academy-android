package com.example.baseproject.user.controllers

import android.util.Log.d
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.BaseFragment
import com.example.baseproject.databinding.FragmentHomeBinding
import com.example.baseproject.user.viewmodels.HomeViewModel
import com.example.baseproject.user.UserDto
import com.example.baseproject.user.UsersAdapter
import com.example.baseproject.utils.showErrorToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val homeViewModel: HomeViewModel by viewModels()

    private val usersAdapter by lazy {
        UsersAdapter()
    }

    override fun start() {
        setUsersAdapter()
    }

    override fun listeners() {
        goToProfilePage()
        observePaging()
        listenAdapter()
    }

    private fun setUsersAdapter() {
        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsers.adapter = usersAdapter
    }

    private fun observePaging() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.usersPagerFlow.collectLatest { usersData: PagingData<UserDto> ->
                    usersAdapter.submitData(usersData)
                }
            }
        }
    }

    private fun listenAdapter() {
        usersAdapter.addLoadStateListener { loadStates ->
            when (loadStates.source.refresh) {
                is LoadState.Loading -> {
                    setLoader(true)
                }
                is LoadState.Error -> {
                    requireContext().showErrorToast((loadStates.source.refresh as LoadState.Error).error.localizedMessage ?: "Error")
                }
                is LoadState.NotLoading -> {
                    setLoader(false)
                    d("UserPagingAdapter", "End of Pagination Reached: ${loadStates.append.endOfPaginationReached}")

                }
            }

            val isEndOfPaginationReached =
                loadStates.append.endOfPaginationReached && loadStates.source.append.endOfPaginationReached

            usersAdapter.setEndOfPaginationReached(isEndOfPaginationReached)
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
