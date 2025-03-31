package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from

import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi

data class FromAccountState(
    val isLoading: Boolean = false,
    val accounts: List<AccountUi>? = null,
    val errorMessage: String? = null
)