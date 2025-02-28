package com.example.tricholog.data.remote.api

import com.example.tricholog.data.remote.dto.ArticleDto
import retrofit2.Response
import retrofit2.http.GET

interface ArticleService {
    @GET("articles")
    suspend fun getArticles(): Response<List<ArticleDto>>
}