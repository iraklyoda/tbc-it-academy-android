package com.example.baseproject.data.di

import com.example.baseproject.common.ApiHelper
import com.example.baseproject.data.UserRepository
import com.example.baseproject.data.local.AuthPreferencesRepository
import com.example.baseproject.data.local.db.AppDatabase
import com.example.baseproject.data.paging.UserRemoteMediator
import com.example.baseproject.data.remote.AuthRepository
import com.example.baseproject.data.remote.api.AuthService
import com.example.baseproject.data.remote.api.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(
        apiHelper: ApiHelper,
        authPreferencesRepository: AuthPreferencesRepository,
        authService: AuthService
    ): AuthRepository {
        return AuthRepository(
            apiHelper = apiHelper,
            authPreferencesRepository = authPreferencesRepository,
            authService = authService
        )
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        database: AppDatabase,
        userRemoteMediator: UserRemoteMediator
    ): UserRepository {
        return UserRepository(database = database, userRemoteMediator = userRemoteMediator)
    }

    @Singleton
    @Provides
    fun provideUserRemoteMediator(
        database: AppDatabase,
        userService: UserService
    ): UserRemoteMediator {
        return UserRemoteMediator(database = database, userService = userService)
    }
}