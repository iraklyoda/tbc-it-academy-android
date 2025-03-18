package com.iraklyoda.categoryapp.presentation.screen.category.model

data class CategoryUi(
    val id: String,
    val name: String,
    val parentCount: Int = 0,
)
