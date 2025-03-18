package com.example.baseproject.di

import com.example.baseproject.data.repository.LogInRepositoryImpl
import com.example.baseproject.data.repository.SignUpRepositoryImpl
import com.example.baseproject.domain.repository.LogInRepository
import com.example.baseproject.domain.repository.SignUpRepository
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