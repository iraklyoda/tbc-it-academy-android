package com.example.baseproject.client

import com.example.baseproject.requests.LoginResponseDto
import com.example.baseproject.requests.RegisterResponseDto
import com.example.baseproject.user.ProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("register")
    suspend fun registerUser(@Body user: ProfileDto): Response<RegisterResponseDto>

    @POST("login")
    suspend fun loginUser(@Body user: ProfileDto): Response<LoginResponseDto>
}