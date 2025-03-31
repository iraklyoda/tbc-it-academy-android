package com.iraklyoda.transferapp.presentation.screen.transfer_internally

import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.model.CurrencyRateUi

data class TransferInternallyState(
    val fromAccount: AccountUi? = null,
    val toAccount: AccountUi? = null,
    val currencyRate: CurrencyRateUi? = null,
    val currencyLoading: Boolean = false,
    val currencyErrorMessage: String? = null
) {
    val needsCurrencyExchange: Boolean?
        get() {
            if (fromAccount == null && toAccount == null) {
                return null
            }

            if (fromAccount == null || toAccount == null) {
                return false
            }

            return fromAccount.valueType != toAccount.valueType
        }
}