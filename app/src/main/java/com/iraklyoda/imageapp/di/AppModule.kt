package com.iraklyoda.imageapp.di

import android.content.Context
import com.iraklyoda.imageapp.data.common.ImageHelper
import com.iraklyoda.imageapp.data.remote.common.ApiHelper
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
    @Singleton
    fun provideApiHelper(): ApiHelper {
        return ApiHelper()
    }

    @Provides
    @Singleton
    fun provideImageHelper(@ApplicationContext appContext: Context): ImageHelper {
        return ImageHelper(appContext = appContext)
    }
}