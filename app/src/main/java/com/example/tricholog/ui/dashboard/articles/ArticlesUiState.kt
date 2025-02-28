package com.example.tricholog.ui.dashboard.articles

import com.example.tricholog.domain.error.ApiError
import com.example.tricholog.domain.model.Article

sealed class ArticlesUiState {
    data object Idle: ArticlesUiState()
    data object Loading: ArticlesUiState()
    data class Success(val articles: List<Article>): ArticlesUiState()
    data class Error(val error: ApiError): ArticlesUiState()
}


/*
sealed class LoginUiState {
    data object Idle: LoginUiState()
    data object Loading: LoginUiState()
    data class Success(val success: Boolean = true): LoginUiState()
    data class Error(val error: AuthError): LoginUiState()
}
 */