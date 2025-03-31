package com.iraklyoda.transferapp.domain.use_case.account

import com.iraklyoda.transferapp.domain.common.Resource
import com.iraklyoda.transferapp.domain.model.GetCurrencyRate
import com.iraklyoda.transferapp.domain.repository.currency.CurrencyRateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetCurrencyRateUseCase {
    suspend operator fun invoke(): Flow<Resource<GetCurrencyRate>>
}

class GetCurrencyRateUseCaseImpl @Inject constructor(
    private val currencyRateRepository: CurrencyRateRepository
): GetCurrencyRateUseCase {
    override suspend fun invoke(): Flow<Resource<GetCurrencyRate>> {
        return currencyRateRepository.getCurrencyRate()
    }

}