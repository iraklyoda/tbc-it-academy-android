package com.iraklyoda.categoryapp.di

import com.iraklyoda.categoryapp.data.repository.CategoryRepositoryImpl
import com.iraklyoda.categoryapp.domain.repository.CategoryRepository
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
    abstract fun bindCategoryRepository(categoryRepository: CategoryRepositoryImpl): CategoryRepository
}