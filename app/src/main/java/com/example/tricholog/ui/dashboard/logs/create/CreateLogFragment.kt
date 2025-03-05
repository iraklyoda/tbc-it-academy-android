package com.example.tricholog.ui.dashboard.logs.create

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tricholog.BaseFragment
import com.example.tricholog.R
import com.example.tricholog.databinding.FragmentCreateLogBinding
import com.example.tricholog.utils.clearError
import com.example.tricholog.utils.getString
import com.example.tricholog.utils.showToast
import com.example.tricholog.utils.toggle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateLogFragment :
    BaseFragment<FragmentCreateLogBinding>(FragmentCreateLogBinding::inflate) {

    private val createLogViewModel: CreateLogViewModel by viewModels()

    override fun start() {
        handleArticleStack()
    }

    override fun listeners() {
        handleCreateState()
        handleReturnBtn()
        onAddLog()
    }


    private fun handleArticleStack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack(R.id.logsFragment, false)
                }
            })
    }

    private fun handleCreateState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                createLogViewModel.logStateFlow.collectLatest { state ->
                    when(state) {
                        is CreateLogState.Loading -> {
                            binding.pbCreateLog.toggle(true)
                        }

                        is CreateLogState.Success -> {
                            handleCreateSuccess()
                        }
                        is CreateLogState.Error -> {
                            requireContext().showToast(state.error.toString())
                            binding.pbCreateLog.toggle(false)
                        }
                        is CreateLogState.Idle -> {
                            binding.pbCreateLog.toggle(false)
                        }
                    }
                }
            }
        }
    }

    private fun onAddLog() {
        binding.btnAddLog.setOnClickListener {
            if (validateForm()) {
                createLogViewModel.createLog(
                    trigger = binding.etTrigger.getString(),
                    body = binding.etBody.getString()
                )
            }
        }
    }

    private fun handleCreateSuccess() {
        requireContext().showToast("Log created successfully")
        binding.pbCreateLog.toggle(false)
        findNavController().popBackStack(R.id.logsFragment, false)
    }

    private fun handleReturnBtn() {
        binding.btnReturn.setOnClickListener {
            findNavController().popBackStack(R.id.logsFragment, false)
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        with(binding) {
            listOf(tlBody, tlTrigger)
                .forEach { it.clearError() }

            if (etTrigger.getString().isEmpty()) {
                isValid = false
                tlTrigger.error = getString(R.string.trigger_field_is_empty)
            }

            if (etBody.getString().isEmpty()) {
                isValid = false
                tlBody.error = getString(R.string.body_field_is_empty)
            }
        }

        return isValid
    }

}