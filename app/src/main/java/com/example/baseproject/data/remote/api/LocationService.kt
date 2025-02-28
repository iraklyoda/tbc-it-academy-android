package com.example.baseproject.data.remote.api

import com.example.baseproject.data.remote.dto.LocationDto
import retrofit2.Response
import retrofit2.http.GET

interface LocationService {
    @GET("c4c64996-4ed9-4cbc-8986-43c4990d495a")
    suspend fun getLocations(): Response<List<LocationDto>>
}