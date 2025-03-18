package com.iraklyoda.categoryapp.presentation.screen.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraklyoda.categoryapp.domain.common.Resource
import com.iraklyoda.categoryapp.domain.use_case.category.GetCategoriesUseCase
import com.iraklyoda.categoryapp.presentation.screen.category.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CategoryState> = MutableStateFlow(CategoryState())
    val state: StateFlow<CategoryState> get() = _state

    private val _uiEvents = Channel<CategoryUiEvent>()
    val uiEvents: Flow<CategoryUiEvent> = _uiEvents.receiveAsFlow()

    fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.FetchCategories -> fetchCategories()
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
                        _state.update { it.copy(categories = resource.data.toUi()) }
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
}