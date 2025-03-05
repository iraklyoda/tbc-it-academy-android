package com.example.tricholog.ui.dashboard.articles.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.repository.ArticleRepository
import com.example.tricholog.ui.mapper.toArticleUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {
    private val _articleStateFlow: MutableStateFlow<ArticleUiState> = MutableStateFlow(
        ArticleUiState.Idle
    )
    val articlesStateFlow get() = _articleStateFlow

    suspend fun getArticle(id: String) {
        viewModelScope.launch {
            articleRepository.getArticle(id).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> _articleStateFlow.value = ArticleUiState.Loading
                    is Resource.Success -> _articleStateFlow.value = ArticleUiState.Success(resource.data.toArticleUi())
                    is Resource.Error -> _articleStateFlow.value = ArticleUiState.Error(resource.error)
                }
            }
        }
    }

}