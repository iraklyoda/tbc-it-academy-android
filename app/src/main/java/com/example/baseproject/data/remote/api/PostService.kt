package com.example.baseproject.data.remote.api

import com.example.baseproject.data.remote.dto.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface PostService {
    @GET("1ba8b612-8391-41e5-8560-98e4a48decc7")
    suspend fun getPosts(): Response<List<PostDto>>
}