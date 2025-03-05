package com.example.tricholog.domain.repository

import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.error.ApiError
import com.example.tricholog.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getArticles(): Flow<Resource<List<Article>, ApiError>>
    suspend fun getArticle(id: String): Flow<Resource<Article, ApiError>>
}