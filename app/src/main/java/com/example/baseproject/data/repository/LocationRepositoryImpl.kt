package com.example.baseproject.data.repository

import com.example.baseproject.data.mapper.toLocationDomain
import com.example.baseproject.data.remote.api.LocationService
import com.example.baseproject.data.remote.common.ApiHelper
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.model.Location
import com.example.baseproject.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val apiService: LocationService,
    private val apiHelper: ApiHelper
): LocationRepository {
    override suspend fun getLocations(): Flow<Resource<List<Location>>> {
        return apiHelper.handleHttpRequest { apiService.getLocations() }
            .map { resource ->
                when(resource) {
                    is Resource.Success -> Resource.Success(resource.data.map { it.toLocationDomain() })
                    is Resource.Loading -> Resource.Loading
                    is Resource.Error -> Resource.Error(errorMessage = resource.errorMessage)
                }
            }
    }

}