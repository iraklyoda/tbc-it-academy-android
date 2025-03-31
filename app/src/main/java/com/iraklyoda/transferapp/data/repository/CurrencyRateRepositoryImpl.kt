package com.iraklyoda.transferapp.data.repository

import com.iraklyoda.transferapp.data.mapper.toDomain
import com.iraklyoda.transferapp.data.remote.common.ApiHelper
import com.iraklyoda.transferapp.data.remote.service.CurrencyRateService
import com.iraklyoda.transferapp.domain.common.Resource
import com.iraklyoda.transferapp.domain.common.mapResource
import com.iraklyoda.transferapp.domain.model.GetCurrencyRate
import com.iraklyoda.transferapp.domain.repository.currency.CurrencyRateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyRateRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val apiService: CurrencyRateService
): CurrencyRateRepository {
    override suspend fun getCurrencyRate(): Flow<Resource<GetCurrencyRate>> {
        return apiHelper.handleHttpRequest {
            apiService.getRates()
        }.mapResource {
            it.toDomain()
        }
    }
}