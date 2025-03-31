package com.iraklyoda.transferapp.presentation.screen.transfer_internally

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.iraklyoda.transferapp.databinding.FragmentTransferInternallyBinding
import com.iraklyoda.transferapp.domain.common.enum.Currency
import com.iraklyoda.transferapp.presentation.BaseFragment
import com.iraklyoda.transferapp.presentation.extensions.collect
import com.iraklyoda.transferapp.presentation.extensions.collectLatest
import com.iraklyoda.transferapp.presentation.extensions.loadImage
import com.iraklyoda.transferapp.presentation.extensions.updateVisibility
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.FromAccountFragment
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.FromAccountFragment.Companion.FROM_ACCOUNT_REQUEST_KEY
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.FromAccountFragment.Companion.SELECTED_ACCOUNT_KEY
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.to.ToAccountFragment
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.to.ToAccountFragment.Companion.RECEIVER_ACCOUNT_KEY
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.to.ToAccountFragment.Companion.TO_ACCOUNT_REQUEST_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferInternallyFragment :
    BaseFragment<FragmentTransferInternallyBinding>(FragmentTransferInternallyBinding::inflate) {

    private val transferInternallyViewModel: TransferInternallyViewModel by viewModels()

    override fun start() {

    }

    override fun listeners() {
        setFromAccountBtnListener()
        setToAccountBtnListener()
        setFromAccountFragmentResultListener()
        setToAccountFragmentResultListener()
        setSellAmountChangedListener()
    }

    override fun observers() {
        handleEvents()
        observeState()
    }


    // Observers

    @SuppressLint("DefaultLocale")
    private fun handleEvents() {
        collect(flow = transferInternallyViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                is TransferInternallyUiEvent.OpenFromAccountDialog -> openFromAccountBottomSheet()
                is TransferInternallyUiEvent.OpenToAccountDialog -> openToAccountBottomSheet()
                is TransferInternallyUiEvent.HandleFetchingError -> {}
                is TransferInternallyUiEvent.UpdateSellAmount -> {
                    binding.etSell.setText(uiEvent.amount.toString())
                }
                is TransferInternallyUiEvent.UpdateBuyAmount -> {
                    binding.etBuy.setText(String.format("%.2f", uiEvent.amount))
                }
            }
        }
    }

    private fun observeState() {
        collectLatest(flow = transferInternallyViewModel.state) { state ->

            binding.apply {
                pbCurrency.isVisible = state.currencyLoading
                btnContinue.isEnabled = state.fromAccount != null && state.toAccount != null

                // From Account
                state.fromAccount?.let {
                    setSelectedFromAccount(account = it)

                    // Type of transaction
                    state.needsCurrencyExchange?.let { exchangeNeeded ->
                        setSameCurrencyLayout(
                            exchangeNeeded = exchangeNeeded,
                            currency = state.fromAccount.valueType
                        )

                        state.toAccount?.let {

                            if (exchangeNeeded)
                                transferInternallyViewModel.onEvent(TransferInternallyEvent.GetCurrencyRate)

                            binding.tvCurrencyInfo.isVisible = exchangeNeeded
                            binding.tvCurrencyInfo.text = state.currencyRate.toString()


                            setExchangeLayout(
                                exchangeNeeded = exchangeNeeded,
                                fromCurrency = state.fromAccount.valueType,
                                toCurrency = state.toAccount.valueType
                            )
                        }
                    }
                }

                state.toAccount?.let {
                    setReceivedToAccount(account = it)
                }
            }
        }
    }

    // Listeners

    private fun setFromAccountBtnListener() {
        binding.btnFrom.setOnClickListener {
            transferInternallyViewModel.onEvent(TransferInternallyEvent.ClickFromAccountBtn)
        }
    }

    private fun setToAccountBtnListener() {
        binding.btnTo.setOnClickListener {
            transferInternallyViewModel.onEvent(TransferInternallyEvent.ClickToAccountBtn)
        }
    }

    private fun setSellAmountChangedListener() {
        binding.etSell.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.isNotEmpty())
                    transferInternallyViewModel.onEvent(TransferInternallyEvent.SellAmountUpdated(amount = text.toString().toDouble()))
            }
        }
    }

    private fun setBuyAmountChangedListener() {
        binding.etBuy.doOnTextChanged { text, _, _, _ ->
            transferInternallyViewModel.onEvent(TransferInternallyEvent.BuyAmountUpdated(amount = text.toString().toDouble()))
        }
    }


    // From Receiver
    private fun setFromAccountFragmentResultListener() {
        setFragmentResultListener(FROM_ACCOUNT_REQUEST_KEY) { requestKey, bundle ->
            if (requestKey == FROM_ACCOUNT_REQUEST_KEY) {
                val selectedAccount: AccountUi? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        bundle.getParcelable(SELECTED_ACCOUNT_KEY, AccountUi::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        bundle.getParcelable(SELECTED_ACCOUNT_KEY)
                    }

                // Handle Result Received Event
                selectedAccount?.let {
                    transferInternallyViewModel.onEvent(
                        TransferInternallyEvent.FromAccountResultReceived(
                            account = it
                        )
                    )
                }
            }
        }
    }

    // To Receiver

    private fun setToAccountFragmentResultListener() {
        setFragmentResultListener(TO_ACCOUNT_REQUEST_KEY) { requestKey, bundle ->
            if (requestKey == TO_ACCOUNT_REQUEST_KEY) {
                val receivedAccount: AccountUi? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        bundle.getParcelable(RECEIVER_ACCOUNT_KEY, AccountUi::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        bundle.getParcelable(RECEIVER_ACCOUNT_KEY)
                    }

                // Handle Result Received Event
                receivedAccount?.let {
                    transferInternallyViewModel.onEvent(
                        TransferInternallyEvent.ToAccountResultReceived(account = it)
                    )
                }
            }
        }
    }

    // Helpers

    private fun openFromAccountBottomSheet() {
        FromAccountFragment().show(
            parentFragmentManager,
            "FromAccountFragment"
        )
    }

    private fun openToAccountBottomSheet() {
        ToAccountFragment().show(
            parentFragmentManager, "ToAccountFragment"
        )
    }

    private fun setSelectedFromAccount(account: AccountUi) {
        binding.apply {
            btnFrom.text = ""
            tvFromName.text = account.accountName
            tvFromAmount.text = account.balance.toString()
            tvFromDigits.text = account.accountNumber.takeLast(4)
            tvFromCurrencySymbol.text = account.valueType.getSymbol()
            ivFromCard.loadImage(source = account.cardLogo)

            root.updateVisibility(
                views = arrayOf(
                    tvFromName,
                    tvFromAmount,
                    tvFromDigits,
                    tvFromCurrencySymbol,
                    tvFromHiddenDigits,
                    ivFromCard
                ), visibility = true
            )
        }
    }

    private fun setReceivedToAccount(account: AccountUi) {
        binding.apply {
            btnTo.text = ""
            tvToName.text = account.accountName
            tvToAmount.text = account.balance.toString()
            tvToDigits.text = account.accountNumber.takeLast(4)
            tvToCurrencySymbol.text = account.valueType.getSymbol()
            ivToCard.loadImage(source = account.cardLogo)

            root.updateVisibility(
                views = arrayOf(
                    tvToName,
                    tvToAmount,
                    tvToDigits,
                    tvToCurrencySymbol,
                    tvToHiddenDigits,
                    ivToCard
                ), visibility = true
            )
        }
    }

    private fun setSameCurrencyLayout(exchangeNeeded: Boolean, currency: Currency) {
        binding.apply {
            tlAmount.isVisible = !exchangeNeeded
            tlAmount.suffixText = currency.getSymbol()
        }
    }

    private fun setExchangeLayout(
        exchangeNeeded: Boolean,
        fromCurrency: Currency,
        toCurrency: Currency
    ) {
        binding.apply {
            root.updateVisibility(
                views = arrayOf(tlBuy, tlSell, tvCurrencyInfo),
                visibility = exchangeNeeded
            )

            Log.d("UPDATE_VISIBILITY", exchangeNeeded.toString())

            tlSell.suffixText = toCurrency.getSymbol()
            tlBuy.suffixText = fromCurrency.getSymbol()
        }
    }
}