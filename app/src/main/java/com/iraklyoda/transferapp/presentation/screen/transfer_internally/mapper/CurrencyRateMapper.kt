package com.iraklyoda.transferapp.presentation.screen.transfer_internally.mapper

import com.iraklyoda.transferapp.domain.model.GetCurrencyRate
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.model.CurrencyRateUi

fun GetCurrencyRate.toUi(): CurrencyRateUi {
    return CurrencyRateUi(usd, eur)
}