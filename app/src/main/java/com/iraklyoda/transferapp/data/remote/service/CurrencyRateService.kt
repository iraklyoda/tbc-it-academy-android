package com.iraklyoda.transferapp.data.remote.service

import com.iraklyoda.transferapp.data.remote.dto.CurrencyRateDto
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyRateService {
    @GET("cf0e54c0-77a0-4489-bb9c-734f7886909e")
    suspend fun getRates(): Response<CurrencyRateDto>
}