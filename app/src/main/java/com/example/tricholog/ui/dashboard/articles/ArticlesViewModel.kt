package com.example.tricholog.ui.dashboard.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.model.Article
import com.example.tricholog.domain.repository.ArticleRepository
import com.example.tricholog.ui.mapper.toArticleUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
): ViewModel() {

    private val _articlesStateFlow: MutableStateFlow<ArticlesUiState> = MutableStateFlow(ArticlesUiState.Idle)
    val articlesStateFlow get() = _articlesStateFlow

    init {
        viewModelScope.launch {
            articleRepository.getArticles().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> _articlesStateFlow.value = ArticlesUiState.Loading
                    is Resource.Success -> _articlesStateFlow.value = ArticlesUiState.Success(resource.data.map { it.toArticleUi() })
                    is Resource.Error -> _articlesStateFlow.value = ArticlesUiState.Error(resource.error)
                }
            }
        }
    }

}