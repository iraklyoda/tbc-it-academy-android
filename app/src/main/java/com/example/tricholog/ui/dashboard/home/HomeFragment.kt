package com.example.tricholog.ui.dashboard.home

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tricholog.BaseFragment
import com.example.tricholog.R
import com.example.tricholog.databinding.FragmentHomeBinding
import com.example.tricholog.ui.dashboard.logs.display.LogsUiState
import com.example.tricholog.utils.showToast
import com.example.tricholog.utils.toggle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun start() {
    }

    override fun listeners() {
        observeLogs()
        onProfilePage()
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getLogs()
    }


    private fun observeLogs() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.logsStateFlow.collectLatest { state ->
                    when (state) {
                        is LogsUiState.Loading -> {
                            binding.pbTimer.toggle(true)
                            binding.tvTimer.visibility = View.GONE
                        }

                        is LogsUiState.Error -> {
                            binding.pbTimer.toggle(false)
                            binding.tvTimer.visibility = View.GONE
                            requireContext().showToast(state.error.toString())
                        }

                        is LogsUiState.Success -> {
                            if (state.logs.isEmpty()) {
                                binding.pbTimer.toggle(false)
                                binding.tvTimer.visibility = View.VISIBLE
                                binding.tvTimer.text = getString(R.string.you_don_t_have_logs_yet)
                            } else {
                                binding.pbTimer.toggle(false)
                                binding.tvTimer.visibility = View.VISIBLE
                                observeTimer()
                            }
                        }

                        is LogsUiState.Idle -> {
                            binding.pbTimer.toggle(false)
                        }
                    }
                }
            }

        }
    }

    private fun observeTimer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.timerStateFlow.collect { timeData ->
                    val formattedTime = getString(
                        R.string.timer_format,
                        timeData.days,
                        timeData.hours,
                        timeData.minutes,
                        timeData.seconds
                    )
                    binding.tvTimer.text = formattedTime
                }
            }
        }
    }

    private fun onProfilePage() {
        binding.btnProfile.setOnClickListener {
            Log.d("Profile Click", "true")
            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            findNavController().navigate(action)
        }
    }
}
