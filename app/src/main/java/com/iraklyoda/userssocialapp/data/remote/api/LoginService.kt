package com.iraklyoda.userssocialapp.data.remote.api

import com.iraklyoda.userssocialapp.data.remote.dto.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponseDto>
}