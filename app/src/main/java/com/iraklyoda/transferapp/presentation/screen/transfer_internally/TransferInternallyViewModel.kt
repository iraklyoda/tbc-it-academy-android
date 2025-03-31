package com.iraklyoda.transferapp.presentation.screen.transfer_internally

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraklyoda.transferapp.domain.common.Resource
import com.iraklyoda.transferapp.domain.common.enum.Currency
import com.iraklyoda.transferapp.domain.use_case.account.GetCurrencyRateUseCase
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferInternallyViewModel @Inject constructor(
    private val getCurrencyRateUseCase: GetCurrencyRateUseCase
) : ViewModel() {

    private val _uiEvent: Channel<TransferInternallyUiEvent> = Channel()
    val uiEvent: Flow<TransferInternallyUiEvent> get() = _uiEvent.receiveAsFlow()

    private val _state: MutableStateFlow<TransferInternallyState> = MutableStateFlow(
        TransferInternallyState()
    )
    val state: StateFlow<TransferInternallyState> get() = _state

    fun onEvent(event: TransferInternallyEvent) {
        when (event) {
            is TransferInternallyEvent.ClickFromAccountBtn -> sendUiEvent(TransferInternallyUiEvent.OpenFromAccountDialog)
            is TransferInternallyEvent.ClickToAccountBtn -> sendUiEvent(TransferInternallyUiEvent.OpenToAccountDialog)
            is TransferInternallyEvent.FromAccountResultReceived -> setFromAccountState(account = event.account)
            is TransferInternallyEvent.ToAccountResultReceived -> setToAccountState(account = event.account)
            is TransferInternallyEvent.GetCurrencyRate -> getCurrencyRate()
            is TransferInternallyEvent.BuyAmountUpdated -> handleBuyAmountUpdated(amount = event.amount)
            is TransferInternallyEvent.SellAmountUpdated -> handleSellAmountUpdated(amount = event.amount)
        }
    }

    private fun handleBuyAmountUpdated(amount: Double) {
        val currencyRate = state.value.currencyRate ?: return

        viewModelScope.launch {
            val toAccount = state.value.fromAccount
            val fromAccount = state.value.toAccount

            if (fromAccount != null && toAccount != null) {
                convertCurrency(
                    from = fromAccount,
                    to = toAccount,
                    amount = amount
                )?.let { convertedAmount ->
                    _uiEvent.send(TransferInternallyUiEvent.UpdateSellAmount(convertedAmount))
                }
            }
        }
    }

    private fun handleSellAmountUpdated(amount: Double) {
        val currencyRate = state.value.currencyRate ?: return

        viewModelScope.launch {
            val toAccount = state.value.toAccount
            val fromAccount = state.value.fromAccount

            if (fromAccount != null && toAccount != null) {
                convertCurrency(
                    from = fromAccount,
                    to = toAccount,
                    amount = amount
                )?.let { convertedAmount ->
                    _uiEvent.send(
                        TransferInternallyUiEvent.UpdateBuyAmount(convertedAmount)
                    )
                }
            }
        }
    }

    private fun convertCurrency(from: AccountUi, to: AccountUi, amount: Double): Double? {

        if (amount == 0.0) {
            return amount
        }

        val exchangeRates = state.value.currencyRate?.let {
            mapOf(
                "GEL" to 1.0,
                "USD" to it.usd,
                "EUR" to it.eur
            )
        } ?: return null

        val amountInGEL = when (from.valueType) {
            Currency.USD -> amount / (exchangeRates["USD"] ?: return null)
            Currency.EUR -> amount / (exchangeRates["EUR"] ?: return null)
            Currency.GEL -> amount
            else -> return null
        }

        return when (to.valueType) {
            Currency.USD -> amountInGEL * (exchangeRates["USD"] ?: return null)
            Currency.EUR -> amountInGEL * (exchangeRates["EUR"] ?: return null)
            Currency.GEL -> amountInGEL
            else -> return null
        }
    }


    private fun getCurrencyRate() {
        if (state.value.currencyRate != null) {
            return
        }

        viewModelScope.launch {
            getCurrencyRateUseCase().collectLatest { resource ->
                when (resource) {
                    is Resource.Loader -> _state.update { it.copy(currencyLoading = resource.loading) }
                    is Resource.Success -> _state.update { it.copy(currencyRate = resource.data.toUi()) }
                    is Resource.Error -> _state.update {
                        it.copy(currencyErrorMessage = resource.errorMessage)
                    }
                }
            }
        }

    }

    private fun setFromAccountState(account: AccountUi) {
        viewModelScope.launch {
            _state.update { it.copy(fromAccount = account) }
        }
    }

    private fun setToAccountState(account: AccountUi) {
        viewModelScope.launch {
            _state.update { it.copy(toAccount = account) }
        }
    }

    private fun sendUiEvent(event: TransferInternallyUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}