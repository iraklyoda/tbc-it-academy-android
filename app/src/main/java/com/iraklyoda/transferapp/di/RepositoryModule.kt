package com.iraklyoda.transferapp.di

import com.iraklyoda.transferapp.data.repository.AccountRepositoryImpl
import com.iraklyoda.transferapp.data.repository.CurrencyRateRepositoryImpl
import com.iraklyoda.transferapp.domain.repository.account.AccountRepository
import com.iraklyoda.transferapp.domain.repository.currency.CurrencyRateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindDebitCardRepository(debitCardRepository: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    fun bindCurrencyRateRepository(currencyRateRepository: CurrencyRateRepositoryImpl): CurrencyRateRepository
}