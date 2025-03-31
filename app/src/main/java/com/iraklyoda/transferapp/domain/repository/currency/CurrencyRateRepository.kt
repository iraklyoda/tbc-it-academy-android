package com.iraklyoda.transferapp.domain.repository.currency

import com.iraklyoda.transferapp.domain.common.Resource
import com.iraklyoda.transferapp.domain.model.GetCurrencyRate
import kotlinx.coroutines.flow.Flow

interface CurrencyRateRepository {
    suspend fun getCurrencyRate(): Flow<Resource<GetCurrencyRate>>
}