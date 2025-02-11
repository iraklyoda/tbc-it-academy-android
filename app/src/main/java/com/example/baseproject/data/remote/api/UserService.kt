package com.example.baseproject.data.remote.api

import com.example.baseproject.data.remote.dto.UserResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun getUsers(@Query("page") nextPageNumber: Int): Response<UserResponseDto>
}