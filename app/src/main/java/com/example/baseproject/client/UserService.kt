package com.example.baseproject.client

import com.example.baseproject.requests.LoginResponseDto
import com.example.baseproject.requests.RegisterResponseDto
import com.example.baseproject.user.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("register")
    suspend fun registerUser(@Body user: UserDto): Response<RegisterResponseDto>

    @POST("login")
    suspend fun loginUser(@Body user: UserDto): Response<LoginResponseDto>
}