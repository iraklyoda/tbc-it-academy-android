package com.example.baseproject.data.remote.api

import com.example.baseproject.data.remote.dto.ProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("register")
    suspend fun registerUser(@Body user: ProfileDto): Response<Unit>
}