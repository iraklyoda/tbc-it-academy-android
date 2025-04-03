package com.iraklyoda.userssocialapp.data.remote.api

import com.iraklyoda.userssocialapp.data.remote.dto.user.UserResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun getUsers(@Query("page") nextPageNumber: Int): Response<UserResponseDto>
}