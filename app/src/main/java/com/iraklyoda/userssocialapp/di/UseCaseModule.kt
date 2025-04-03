package com.iraklyoda.userssocialapp.di

import com.iraklyoda.userssocialapp.domain.use_case.auth.LogInUserUseCase
import com.iraklyoda.userssocialapp.domain.use_case.auth.LogInUserUseCaseImpl
import com.iraklyoda.userssocialapp.domain.use_case.auth.SignUpUserUseCase
import com.iraklyoda.userssocialapp.domain.use_case.auth.SignUpUserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    @Singleton
    @Binds
    fun bindLoginUserUseCase(logInUserUseCase: LogInUserUseCaseImpl): LogInUserUseCase

    @Singleton
    @Binds
    fun bindRegisterUserUseCase(registerUserUseCase: SignUpUserUseCaseImpl): SignUpUserUseCase
}