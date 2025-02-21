package com.example.baseproject.data.di

import android.content.Context
import com.example.baseproject.data.local.AuthPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Singleton
    @Provides
    fun provideAuthPreferencesRepository(@ApplicationContext context: Context): AuthPreferencesRepository {
        return AuthPreferencesRepository(context.dataStore)
    }
}