package com.example.baseproject.data.di

import com.example.baseproject.common.ApiHelper
import com.example.baseproject.data.remote.api.LocationService
import com.example.baseproject.data.remote.api.PostService
import com.example.baseproject.data.repository.LocationRepository
import com.example.baseproject.data.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideLocationRepository(
        locationService: LocationService,
        apiHelper: ApiHelper
    ): LocationRepository {
        return LocationRepository(locationService, apiHelper)
    }

    @Provides
    @ViewModelScoped
    fun providePostRepository(
        postService: PostService,
        apiHelper: ApiHelper
    ): PostRepository {
        return PostRepository(postService, apiHelper)
    }
}