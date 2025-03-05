package com.example.tricholog.ui.dashboard.articles

import com.example.tricholog.domain.error.ApiError
import com.example.tricholog.ui.dashboard.articles.model.ArticleUi

sealed class ArticlesUiState {
    data object Idle: ArticlesUiState()
    data object Loading: ArticlesUiState()
    data class Success(val articles: List<ArticleUi>): ArticlesUiState()
    data class Error(val error: ApiError): ArticlesUiState()
}