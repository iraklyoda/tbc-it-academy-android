package com.example.baseproject.data.di

import com.example.baseproject.data.repository.LogInRepositoryImpl
import com.example.baseproject.data.repository.LogOutRepositoryImpl
import com.example.baseproject.data.repository.SignUpRepositoryImpl
import com.example.baseproject.domain.repository.LogInRepository
import com.example.baseproject.domain.repository.LogOutRepository
import com.example.baseproject.domain.repository.SignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: LogInRepositoryImpl): LogInRepository

    @Binds
    abstract fun bindRegisterRepository(registerRepositoryImpl: SignUpRepositoryImpl): SignUpRepository

    @Binds
    abstract fun bindLogOutRepository(logOutRepositoryImpl: LogOutRepositoryImpl): LogOutRepository

}