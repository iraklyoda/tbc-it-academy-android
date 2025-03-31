package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.event

import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi

sealed class FromAccountUiEvent {
    data class HandleChosenAccount(val account: AccountUi): FromAccountUiEvent()
    data class HandleFetchingError(val errorMessage: String) : FromAccountUiEvent()
}