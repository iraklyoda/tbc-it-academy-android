package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.iraklyoda.transferapp.databinding.FragmentFromAccountBinding
import com.iraklyoda.transferapp.presentation.BaseBottomSheetDialogFragment
import com.iraklyoda.transferapp.presentation.extensions.collect
import com.iraklyoda.transferapp.presentation.extensions.collectLatest
import com.iraklyoda.transferapp.presentation.extensions.showSnackbar
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.adapter.FromAccountAdapter
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.event.FromAccountEvent
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.event.FromAccountUiEvent
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FromAccountFragment :
    BaseBottomSheetDialogFragment<FragmentFromAccountBinding>(FragmentFromAccountBinding::inflate) {

    private val fromAccountViewModel: FromAccountViewModel by viewModels()
    private lateinit var fromAccountAdapter: FromAccountAdapter

    override fun start() {
        setFromAccountAdapter()
        fromAccountViewModel.onEvent(FromAccountEvent.FetchDebitCards)
    }

    override fun listeners() {
    }

    override fun observers() {
        observeFromConnectionState()
        handleEvents()
    }

    // Set Up

    private fun setFromAccountAdapter() {
        fromAccountAdapter = FromAccountAdapter { account ->
            fromAccountViewModel.onEvent(event = FromAccountEvent.AccountClicked(account = account))
        }
        binding.apply {
            rvCards.layoutManager = LinearLayoutManager(requireContext())
            rvCards.adapter = fromAccountAdapter
        }
    }

    // Observers

    private fun observeFromConnectionState() {
        collectLatest(flow = fromAccountViewModel.state) { state ->
            fromAccountAdapter.submitList(state.accounts)
            binding.pbAccounts.isVisible = state.isLoading
        }
    }

    private fun handleEvents() {
        collect(flow = fromAccountViewModel.event) { event ->
            when(event) {
                is FromAccountUiEvent.HandleChosenAccount -> handleChosenAccount(account = event.account)
                is FromAccountUiEvent.HandleFetchingError -> handleFetchingError(errorMessage = event.errorMessage)
            }
        }
    }

    // Helpers

    private fun handleChosenAccount(account: AccountUi) {
        val resultBundle = Bundle().apply {
            putParcelable(SELECTED_ACCOUNT_KEY, account)
        }
        setFragmentResult(FROM_ACCOUNT_REQUEST_KEY, resultBundle)
        dismiss()
    }

    private fun handleFetchingError(errorMessage: String) {
        binding.root.showSnackbar(message = errorMessage)
        dismiss()
    }


    companion object {
        const val FROM_ACCOUNT_REQUEST_KEY = "fromAccountSelectionRequest"
        const val SELECTED_ACCOUNT_KEY = "selectedAccountBundleKey"
    }

}