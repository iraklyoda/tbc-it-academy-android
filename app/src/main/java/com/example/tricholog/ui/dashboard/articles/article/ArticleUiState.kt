package com.example.tricholog.ui.dashboard.articles.article

import com.example.tricholog.domain.error.ApiError
import com.example.tricholog.ui.dashboard.articles.model.ArticleUi

sealed class ArticleUiState {
    data object Idle: ArticleUiState()
    data object Loading: ArticleUiState()
    data class Success(val article: ArticleUi): ArticleUiState()
    data class Error(val error: ApiError): ArticleUiState()
}