package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.to

import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi

data class ToAccountState(
    val account: AccountUi? = null,
    val errorMessage: String? = null,
    val sendInputErrorMessage: String? = null,
    val isLoading: Boolean = false
)