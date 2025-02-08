package com.example.baseproject.presentation.home

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentHomeBinding
import com.example.baseproject.data.remote.dto.UserDto
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
        binding.rvUsers.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvUsers.adapter = usersAdapter

        val dividerDrawable: Drawable? =
            ContextCompat.getDrawable(requireContext(), R.drawable.item_user_divider)
        dividerDrawable?.let {
            val userDivider =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            userDivider.setDrawable(it)
            binding.rvUsers.addItemDecoration(userDivider)
        }
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

            if (loadStates.append is LoadState.Loading) {
                setLoader(true)
            }

            when (loadStates.source.refresh) {
                is LoadState.Loading -> {
                    setLoader(true)
                }

                is LoadState.Error -> {
                    requireContext().showErrorToast(
                        (loadStates.source.refresh as LoadState.Error).error.localizedMessage
                            ?: "Error"
                    )
                }

                is LoadState.NotLoading -> {
                    setLoader(false)
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
