package com.iraklyoda.categoryapp.presentation.screen.category.mapper

import com.iraklyoda.categoryapp.domain.model.GetCategory
import com.iraklyoda.categoryapp.presentation.screen.category.model.CategoryUi

fun GetCategory.toUi(): CategoryUi {
    return CategoryUi(
        id = id,
        name = name,
    )
}

fun List<GetCategory>.toUi(): List<CategoryUi> {

    fun flatten(categories: List<GetCategory>, parentCount: Int = 0): List<CategoryUi> {
        return categories.flatMap { category ->
            listOf(
                CategoryUi(
                    id = category.id,
                    name = category.name,
                    parentCount = parentCount
                )
            ) + flatten(category.children, parentCount + 1)
        }
    }

    return flatten(categories = this)
}