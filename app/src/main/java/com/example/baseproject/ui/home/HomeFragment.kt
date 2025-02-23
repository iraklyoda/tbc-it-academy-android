package com.example.baseproject.ui.home

import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.BaseFragment
import com.example.baseproject.common.Resource
import com.example.baseproject.databinding.FragmentHomeBinding
import com.example.baseproject.ui.home.location.Location
import com.example.baseproject.ui.home.location.LocationAdapter
import com.example.baseproject.ui.home.post.PostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    private val locationAdapter by lazy {
        LocationAdapter()
    }

    private val postAdapter by lazy {
        PostAdapter()
    }

    override fun start() {
        setLocationAdapter()
        observeLocations()

        setPostAdapter()
        observePosts()
    }

    override fun listeners() {

    }

    private fun setLocationAdapter() {
        binding.rvLocations.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = locationAdapter
        }
        locationAdapter.attachItemDecoration(binding.rvLocations)
    }

    private fun setPostAdapter() {
        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = postAdapter
        }
    }

    private fun observeLocations() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.locationsStateFlow.collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        if (resource.loading) {
                            binding.pbLocations.visibility = View.VISIBLE
                        } else {
                            binding.pbLocations.visibility = View.GONE
                        }
                    }
                    is Resource.Success -> {
                        d("LocationSuccess", "${resource.data}")
                        locationAdapter.submitList(resource.data)
                    }
                    is Resource.Error -> {
                        binding.rvLocations.visibility = View.GONE
                        Toast.makeText(requireContext(), resource.errorMessage, Toast.LENGTH_SHORT).show()
                        d("LocationError", resource.errorMessage)
                    }
                }
            }
        }
    }

    private fun observePosts() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.postsStateFlow.collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        if (resource.loading) {
                            binding.pbPosts.visibility = View.VISIBLE
                        } else {
                            binding.pbPosts.visibility = View.GONE
                        }
                    }
                    is Resource.Success -> {
                        d("LocationSuccess", "${resource.data}")
                        postAdapter.submitList(resource.data)
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), resource.errorMessage, Toast.LENGTH_SHORT).show()
                        d("PostError", resource.errorMessage)
                    }
                }
            }
        }
    }
}