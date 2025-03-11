package com.example.baseproject.ui.home

import android.graphics.drawable.Drawable
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
import com.example.baseproject.ui.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentHomeBinding
import com.example.baseproject.utils.setLoaderState
import com.example.baseproject.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
                homeViewModel.usersPagingFlow.collectLatest { usersData: PagingData<User> ->
                    usersAdapter.submitData(usersData)
                }
            }
        }
    }

    private fun listenAdapter() {
        usersAdapter.addLoadStateListener { loadStates ->

            if (loadStates.append is LoadState.Loading) {
                binding.pbUsers.setLoaderState(true)
            }

            when (loadStates.source.refresh) {
                is LoadState.Loading -> {
                    binding.pbUsers.setLoaderState(true)
                }

                is LoadState.Error -> {
                    requireContext().showErrorToast(
                        (loadStates.source.refresh as LoadState.Error).error.localizedMessage
                            ?: "Error"
                    )
                    binding.pbUsers.setLoaderState(false)
                }

                is LoadState.NotLoading -> {
                    binding.pbUsers.setLoaderState(false)
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
}
