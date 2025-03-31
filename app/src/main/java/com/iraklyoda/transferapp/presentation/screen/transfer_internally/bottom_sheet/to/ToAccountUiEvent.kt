package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.to

import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi

sealed class ToAccountUiEvent {
    data class HandleFetchingError(val errorMessage: String) : ToAccountUiEvent()
    data class HandleAccountReceived(val account: AccountUi): ToAccountUiEvent()
}