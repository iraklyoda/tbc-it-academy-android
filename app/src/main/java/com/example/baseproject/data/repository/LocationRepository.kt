package com.example.baseproject.data.repository

import com.example.baseproject.common.ApiHelper
import com.example.baseproject.common.Resource
import com.example.baseproject.data.mapper.toLocationUI
import com.example.baseproject.data.remote.api.LocationService
import com.example.baseproject.ui.home.location.Location
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class LocationRepository @Inject constructor(
    private val locationService: LocationService,
    private val apiHelper: ApiHelper
) {
    suspend fun getLocations(): Flow<Resource<List<Location>>> {
        return apiHelper.handleHttpRequest { locationService.getLocations() }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data.map { it.toLocationUI() })
                    is Resource.Error -> Resource.Error(resource.errorMessage)
                    is Resource.Loading -> Resource.Loading(resource.loading)
                }
            }
    }
}