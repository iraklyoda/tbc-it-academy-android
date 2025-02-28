package com.example.baseproject.data.di

import com.example.baseproject.data.repository.LocationRepositoryImpl
import com.example.baseproject.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationsRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository
}