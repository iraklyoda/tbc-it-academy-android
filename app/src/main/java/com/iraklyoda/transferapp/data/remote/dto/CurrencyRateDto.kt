package com.iraklyoda.transferapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateDto(
    @SerialName("USD") val usd: Double,
    @SerialName("EUR") val eur: Double
)