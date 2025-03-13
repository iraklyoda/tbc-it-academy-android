package com.example.baseproject.data.remote.api

import com.example.baseproject.data.remote.dto.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterService {
    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<RegisterResponseDto>
}