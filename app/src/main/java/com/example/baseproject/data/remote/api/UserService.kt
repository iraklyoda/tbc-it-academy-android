package com.example.baseproject.data.remote.api

import com.example.baseproject.data.remote.dto.UserResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun getUsersData(@Query("page") nextPageNumber: Int = 1): Response<UserResponseDto>
}