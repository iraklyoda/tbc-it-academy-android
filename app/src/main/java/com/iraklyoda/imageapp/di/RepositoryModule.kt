package com.iraklyoda.imageapp.di

import com.iraklyoda.imageapp.data.repository.ImageRepositoryImpl
import com.iraklyoda.imageapp.domain.common.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindImageRepository(imageRepository: ImageRepositoryImpl): ImageRepository
}