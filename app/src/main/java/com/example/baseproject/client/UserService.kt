package com.example.baseproject.client

import com.example.baseproject.requests.LoginResponseDto
import com.example.baseproject.requests.RegisterResponseDto
import com.example.baseproject.user.ProfileDto
import com.example.baseproject.user.UserDto
import com.example.baseproject.user.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("register")
    suspend fun registerUser(@Body user: ProfileDto): Response<RegisterResponseDto>

    @POST("login")
    suspend fun loginUser(@Body user: ProfileDto): Response<LoginResponseDto>

    @GET("users")
    suspend fun getUsersData(@Query("page") nextPageNumber: Int = 1): Response<UserResponseDto>
}