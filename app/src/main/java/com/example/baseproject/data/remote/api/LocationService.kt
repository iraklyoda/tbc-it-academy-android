package com.example.baseproject.data.remote.api

import com.example.baseproject.data.remote.dto.LocationDto
import retrofit2.Response
import retrofit2.http.GET

interface LocationService {
    @GET("00a18030-a8c7-47c4-b0c5-8bff92a29ebf")
    suspend fun getLocations(): Response<List<LocationDto>>
}