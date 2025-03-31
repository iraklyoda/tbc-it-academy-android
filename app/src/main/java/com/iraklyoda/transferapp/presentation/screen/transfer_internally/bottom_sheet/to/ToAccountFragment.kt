package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.to

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.iraklyoda.transferapp.databinding.FragmentToAccountBinding
import com.iraklyoda.transferapp.presentation.BaseBottomSheetDialogFragment
import com.iraklyoda.transferapp.presentation.extensions.collect
import com.iraklyoda.transferapp.presentation.extensions.collectLatest
import com.iraklyoda.transferapp.presentation.extensions.updateInvisibility
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToAccountFragment :
    BaseBottomSheetDialogFragment<FragmentToAccountBinding>(FragmentToAccountBinding::inflate) {

    private val toAccountViewModel: ToAccountViewModel by viewModels()

    override fun start() {}

    override fun listeners() {
        setCheckBtnListener()
    }

    override fun observers() {
        observeState()
        handleEvents()
    }

    // Observers

    private fun observeState() {
        collectLatest(flow = toAccountViewModel.state) { state ->
            binding.apply {
                // Input Validation
                tlIdentifier.error = state.sendInputErrorMessage

                // Loader
                pbAccount.isVisible = state.isLoading
                root.updateInvisibility(
                    views = arrayOf(tvHeader, tlIdentifier, btnCheck),
                    inVisibility = state.isLoading
                )
            }
        }
    }

    private fun handleEvents() {
        collect(flow = toAccountViewModel.uiEvents) { event ->
            when (event) {
                is ToAccountUiEvent.HandleFetchingError -> handleFetchingError(errorMessage = event.errorMessage)
                is ToAccountUiEvent.HandleAccountReceived -> handleReceivedAccount(account = event.account)
            }
        }
    }

    // Listeners

    private fun setCheckBtnListener() {
        binding.btnCheck.setOnClickListener {
            toAccountViewModel.onEvent(
                event = ToAccountEvent.CheckIdClicked(
                    identifier = binding.etIdentifier.text?.trim().toString()
                )
            )
        }
    }

    // Helpers

    private fun handleReceivedAccount(account: AccountUi) {
        val resultBundle = Bundle().apply {
            putParcelable(RECEIVER_ACCOUNT_KEY, account)
        }
        setFragmentResult(TO_ACCOUNT_REQUEST_KEY, resultBundle)
        dismiss()
    }

    private fun handleFetchingError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        dismiss()
    }

    companion object {
        const val TO_ACCOUNT_REQUEST_KEY = "toAccountSelectionRequest"
        const val RECEIVER_ACCOUNT_KEY = "receiverAccountBundleKey"
    }
}