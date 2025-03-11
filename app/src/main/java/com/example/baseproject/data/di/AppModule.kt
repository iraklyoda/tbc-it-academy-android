package com.example.baseproject.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.baseproject.data.UserRepository
import com.example.baseproject.data.local.db.AppDatabase
import com.example.baseproject.data.paging.UserRemoteMediator
import com.example.baseproject.data.remote.api.UserService
import com.example.baseproject.data.remote.common.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApiHelper(): ApiHelper {
        return ApiHelper()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Singleton
    @Provides
    fun provideUserRemoteMediator(
        database: AppDatabase,
        userService: UserService
    ): UserRemoteMediator {
        return UserRemoteMediator(database = database, userService = userService)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        database: AppDatabase,
        userRemoteMediator: UserRemoteMediator
    ): UserRepository {
        return UserRepository(database = database, userRemoteMediator = userRemoteMediator)
    }
}