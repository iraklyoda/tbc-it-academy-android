package com.iraklyoda.categoryapp.presentation.screen.category

sealed interface CategoryUiEvent {
    data class ShowErrorSnackBar(val errorMessage: String): CategoryUiEvent
}