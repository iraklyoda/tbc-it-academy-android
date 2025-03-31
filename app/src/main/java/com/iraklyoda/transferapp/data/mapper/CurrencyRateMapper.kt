package com.iraklyoda.transferapp.data.mapper

import com.iraklyoda.transferapp.data.remote.dto.CurrencyRateDto
import com.iraklyoda.transferapp.domain.model.GetCurrencyRate

fun CurrencyRateDto.toDomain(): GetCurrencyRate {
    return GetCurrencyRate(eur, usd)
}