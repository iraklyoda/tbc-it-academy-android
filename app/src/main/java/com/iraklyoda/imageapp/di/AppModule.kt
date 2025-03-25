package com.iraklyoda.imageapp.di

import android.media.Image
import com.google.firebase.storage.FirebaseStorage
import com.iraklyoda.imageapp.data.remote.common.ApiHelper
import com.iraklyoda.imageapp.data.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideImageRepository(firebaseStorage: FirebaseStorage): ImageRepository {
        return ImageRepository(firebaseStorage = firebaseStorage)
    }
}