package com.iraklyoda.categoryapp.presentation.screen.category.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraklyoda.categoryapp.domain.common.Resource
import com.iraklyoda.categoryapp.domain.use_case.category.GetCategoriesUseCase
import com.iraklyoda.categoryapp.domain.use_case.category.SearchCategoriesUseCase
import com.iraklyoda.categoryapp.presentation.screen.category.CategoryEvent
import com.iraklyoda.categoryapp.presentation.screen.category.CategoryState
import com.iraklyoda.categoryapp.presentation.screen.category.CategoryUiEvent
import com.iraklyoda.categoryapp.presentation.screen.category.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val searchCategoriesUseCase: SearchCategoriesUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CategoryState> = MutableStateFlow(CategoryState())
    val state: StateFlow<CategoryState> get() = _state

    private val _uiEvents = Channel<CategoryUiEvent>()
    val uiEvents: Flow<CategoryUiEvent> = _uiEvents.receiveAsFlow()

    private val _searchQuery = MutableStateFlow("")
    private var searchStarted: Boolean = false

    fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.FetchCategories -> fetchCategories()
            is CategoryEvent.SearchCategories -> {
                startSearchQueryCollection()
                searchStarted = true
                _searchQuery.value = event.query
            }
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(loader = resource.loading) }
                    }

                    is Resource.Success -> {
                        _state.update { it.copy(categories = resource.data.map { category -> category.toUi() }) }
                    }

                    is Resource.Error -> {
                        _state.update { it.copy(error = resource.errorMessage) }
                        _uiEvents.send(
                            CategoryUiEvent.ShowErrorSnackBar(
                                errorMessage = state.value.error ?: "Unknown Error"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun searchCategories(query: String) {
        viewModelScope.launch {
            searchCategoriesUseCase(query = query).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(searchLoader = resource.loading) }
                    }

                    is Resource.Success -> {
                        Log.d("searchedCategories", "${resource.data}")
                        _state.update { it.copy(categories = resource.data.map { category -> category.toUi() }) }
                    }

                    is Resource.Error -> {
                        _state.update { it.copy(error = resource.errorMessage) }
                        _uiEvents.send(
                            CategoryUiEvent.ShowErrorSnackBar(
                                errorMessage = state.value.error ?: "Unknown Error"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun startSearchQueryCollection() {
        if (searchStarted) return
        viewModelScope.launch {
            _searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    searchCategories(query = query)
                }
        }
    }
}