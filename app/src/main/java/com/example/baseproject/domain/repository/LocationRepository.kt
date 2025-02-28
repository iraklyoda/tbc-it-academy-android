package com.example.baseproject.domain.repository

import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getLocations(): Flow<Resource<List<Location>>>
}