package com.example.baseproject.client

import com.example.baseproject.requests.RegisterResponseDto
import com.example.baseproject.user.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/register")
    suspend fun registerUser(@Body user: UserDto): Response<RegisterResponseDto>

    @POST("api/login")
    suspend fun loginUser(@Body user: UserDto): Response<String>
}