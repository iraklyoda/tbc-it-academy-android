package com.iraklyoda.transferapp.di

import com.iraklyoda.transferapp.domain.use_case.account.GetAccountsUseCase
import com.iraklyoda.transferapp.domain.use_case.account.GetAccountsUseCaseImpl
import com.iraklyoda.transferapp.domain.use_case.account.GetCurrencyRateUseCase
import com.iraklyoda.transferapp.domain.use_case.account.GetCurrencyRateUseCaseImpl
import com.iraklyoda.transferapp.domain.use_case.account.GetReceiverAccountUseCase
import com.iraklyoda.transferapp.domain.use_case.account.GetReceiverAccountUseCaseImpl
import com.iraklyoda.transferapp.domain.use_case.account.ValidateAccountIdUseCase
import com.iraklyoda.transferapp.domain.use_case.account.ValidateAccountIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    @Singleton
    fun bindGetDebitCardsUseCase(getDebitCardsUseCase: GetAccountsUseCaseImpl): GetAccountsUseCase

    @Binds
    @Singleton
    fun bindGetReceiverAccountUseCase(getReceiverAccountUseCase: GetReceiverAccountUseCaseImpl): GetReceiverAccountUseCase

    @Binds
    @Singleton
    fun bindGetCurrencyRateUseCase(getCurrencyRateUseCase: GetCurrencyRateUseCaseImpl): GetCurrencyRateUseCase

    @Binds
    @Singleton
    fun validateAccountIdUseCase(validateAccountIdUseCase: ValidateAccountIdUseCaseImpl): ValidateAccountIdUseCase
}