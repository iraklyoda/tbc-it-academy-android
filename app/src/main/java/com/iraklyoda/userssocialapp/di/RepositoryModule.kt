package com.iraklyoda.userssocialapp.di

import com.iraklyoda.userssocialapp.data.repository.LogInRepositoryImpl
import com.iraklyoda.userssocialapp.data.repository.SignUpRepositoryImpl
import com.iraklyoda.userssocialapp.domain.repository.LogInRepository
import com.iraklyoda.userssocialapp.domain.repository.SignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: LogInRepositoryImpl): LogInRepository

    @Singleton
    @Binds
    abstract fun bindRegisterRepository(registerRepositoryImpl: SignUpRepositoryImpl): SignUpRepository
}