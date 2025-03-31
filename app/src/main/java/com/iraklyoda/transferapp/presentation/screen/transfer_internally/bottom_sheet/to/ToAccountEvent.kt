package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.to

sealed class ToAccountEvent {
    data class CheckIdClicked(val identifier: String): ToAccountEvent()
}