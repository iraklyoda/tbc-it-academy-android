package com.example.baseproject.presentation

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.baseproject.BaseFragment
import com.example.baseproject.common.Resource
import com.example.baseproject.databinding.FragmentStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class StatisticsFragment :
    BaseFragment<FragmentStatisticsBinding>(FragmentStatisticsBinding::inflate) {
    private val statisticsViewModel: StatisticsViewModel by viewModels()
    private val viewPagerAdapter by lazy {
        ViewPagerAdapter()
    }

    override fun start() {
        setAdapter()
    }

    override fun listeners() {
        observeStatistics()
    }

    private fun observeStatistics() {
        viewLifecycleOwner.lifecycleScope.launch {
            statisticsViewModel.statisticsStateFlow.collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        if (resource.loading) {
                            binding.pbStatistics.visibility = View.VISIBLE
                        } else {
                            binding.pbStatistics.visibility = View.GONE
                        }
                    }

                    is Resource.Success -> {
                        viewPagerAdapter.submitList(resource.data)
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), resource.errorMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        binding.vp2.adapter = viewPagerAdapter

        val transformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val scaleFactor = 0.85f + (1 - abs(position)) * 0.14f
                page.scaleY = scaleFactor
            }
        }

        binding.vp2.apply {
            adapter = viewPagerAdapter
            setPageTransformer(transformer)
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            setPadding(80, 0, 80, 0)
        }
    }

}