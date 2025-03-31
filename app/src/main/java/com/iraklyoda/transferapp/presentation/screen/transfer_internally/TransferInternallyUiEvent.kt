package com.iraklyoda.transferapp.presentation.screen.transfer_internally

sealed class TransferInternallyUiEvent {
    data object OpenFromAccountDialog: TransferInternallyUiEvent()
    data object OpenToAccountDialog: TransferInternallyUiEvent()
    data class UpdateSellAmount(val amount: Double): TransferInternallyUiEvent()
    data class UpdateBuyAmount(val amount: Double): TransferInternallyUiEvent()
    data class HandleFetchingError(val errorMessage: String) : TransferInternallyUiEvent()
}