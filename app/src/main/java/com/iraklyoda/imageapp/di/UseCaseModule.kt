package com.iraklyoda.imageapp.di

import com.iraklyoda.imageapp.domain.common.use_case.UploadImageUseCase
import com.iraklyoda.imageapp.domain.common.use_case.UploadImageUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindUploadImageUseCase(uploadImageUseCase: UploadImageUseCaseImpl): UploadImageUseCase
}