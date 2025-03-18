package com.iraklyoda.categoryapp.presentation.screen.category

import com.iraklyoda.categoryapp.presentation.screen.category.model.CategoryUi

data class CategoryState(
    var loader: Boolean = false,
    var searchLoader: Boolean = false,
    var error: String? = null,
    var categories: List<CategoryUi>? = null
)