package com.iraklyoda.categoryapp.di

import com.iraklyoda.categoryapp.domain.use_case.category.GetCategoriesUseCase
import com.iraklyoda.categoryapp.domain.use_case.category.GetCategoriesUseCaseImpl
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
    abstract fun bindGetCategoriesUseCase(getCategoryUseCase: GetCategoriesUseCaseImpl): GetCategoriesUseCase
}