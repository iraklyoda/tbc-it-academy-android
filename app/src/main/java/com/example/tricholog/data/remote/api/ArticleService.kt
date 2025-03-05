package com.example.tricholog.data.remote.api

import com.example.tricholog.data.remote.dto.ArticleDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleService {
    @GET("articles")
    suspend fun getArticles(): Response<List<ArticleDto>>

    @GET("articles/{id}")
    suspend fun getArticle(@Path("id") articleId: String): Response<ArticleDto>
}