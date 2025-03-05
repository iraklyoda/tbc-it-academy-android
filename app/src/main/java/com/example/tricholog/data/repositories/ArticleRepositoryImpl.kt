package com.example.tricholog.data.repositories

import com.example.tricholog.data.mapper.toArticleDomain
import com.example.tricholog.data.remote.api.ArticleService
import com.example.tricholog.data.remote.common.ApiHelper
import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.error.ApiError
import com.example.tricholog.domain.model.Article
import com.example.tricholog.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val apiService: ArticleService,
    private val apiHelper: ApiHelper
) : ArticleRepository {

    override suspend fun getArticles(): Flow<Resource<List<Article>, ApiError>> {
        return apiHelper.handleHttpRequest { apiService.getArticles() }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data.map { it.toArticleDomain() })
                    is Resource.Error -> Resource.Error(resource.error)
                    is Resource.Loading -> Resource.Loading
                }
            }
    }

    override suspend fun getArticle(id: String): Flow<Resource<Article, ApiError>> {
        return apiHelper.handleHttpRequest { apiService.getArticle(id) }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data.toArticleDomain())
                    is Resource.Error -> Resource.Error(resource.error)
                    is Resource.Loading -> Resource.Loading
                }
            }
    }
}