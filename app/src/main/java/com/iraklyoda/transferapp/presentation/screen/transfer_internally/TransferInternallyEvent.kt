package com.iraklyoda.transferapp.presentation.screen.transfer_internally

import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi

sealed class TransferInternallyEvent {
    data object ClickFromAccountBtn: TransferInternallyEvent()
    data object ClickToAccountBtn: TransferInternallyEvent()
    data class FromAccountResultReceived(val account: AccountUi): TransferInternallyEvent()
    data class ToAccountResultReceived(val account: AccountUi): TransferInternallyEvent()

    data class SellAmountUpdated(val amount: Double): TransferInternallyEvent()
    data class BuyAmountUpdated(val amount: Double): TransferInternallyEvent()
    data object GetCurrencyRate: TransferInternallyEvent()
}