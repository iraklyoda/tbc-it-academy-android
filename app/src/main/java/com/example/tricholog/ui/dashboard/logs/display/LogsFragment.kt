package com.example.tricholog.ui.dashboard.logs.display

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tricholog.BaseFragment
import com.example.tricholog.databinding.FragmentLogsBinding
import com.example.tricholog.utils.showToast
import com.example.tricholog.utils.toggle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogsFragment : BaseFragment<FragmentLogsBinding>(FragmentLogsBinding::inflate) {

    private val logsViewModel: LogsViewModel by viewModels()

    private val logsAdapter by lazy {
        LogsAdapter()
    }

    override fun start() {
        setLogsAdapter()
    }

    override fun listeners() {
        onAddBtnClick()
        observeLogs()
    }

    override fun onResume() {
        super.onResume()
        logsViewModel.getLogs()
    }

    private fun setLogsAdapter() {
        binding.rvLogs.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvLogs.adapter = logsAdapter
    }

    private fun observeLogs() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                logsViewModel.logsStateFlow.collectLatest { state ->
                    when (state) {
                        is LogsUiState.Loading -> {
                            binding.pbLogs.toggle(true)
                        }

                        is LogsUiState.Error -> {
                            binding.pbLogs.toggle(false)
                            requireContext().showToast(state.error.toString())
                        }

                        is LogsUiState.Success -> {
                            binding.pbLogs.toggle(false)
                            logsAdapter.submitList(state.logs.reversed())
                            binding.rvLogs.scrollToPosition(0)
                        }

                        is LogsUiState.Idle -> {
                            binding.pbLogs.toggle(false)
                        }
                    }
                }
            }

        }
    }

    private fun onAddBtnClick() {
        binding.btnAdd.setOnClickListener {
            val action = LogsFragmentDirections.actionLogsFragmentToCreateLogFragment()
            findNavController().navigate(action)
        }
    }

}