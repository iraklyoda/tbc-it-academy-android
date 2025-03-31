package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.event

import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi

sealed class FromAccountEvent {
    data object FetchDebitCards: FromAccountEvent()
    data class AccountClicked(val account: AccountUi): FromAccountEvent()
}