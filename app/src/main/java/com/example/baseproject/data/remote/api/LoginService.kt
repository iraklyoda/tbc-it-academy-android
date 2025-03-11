package com.example.baseproject.data.remote.api

import com.example.baseproject.data.remote.dto.LoginResponseDto
import com.example.baseproject.data.remote.dto.ProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun loginUser(@Body user: ProfileDto): Response<LoginResponseDto>
}