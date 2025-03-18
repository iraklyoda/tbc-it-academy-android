package com.iraklyoda.categoryapp.presentation.screen.category

sealed interface CategoryEvent {
    data object FetchCategories: CategoryEvent
    data class SearchCategories(val query: String): CategoryEvent
}