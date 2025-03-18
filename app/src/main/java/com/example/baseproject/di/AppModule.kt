package com.example.baseproject.di

import com.example.baseproject.data.remote.common.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApiHelper(): ApiHelper {
        return ApiHelper()
    }
}