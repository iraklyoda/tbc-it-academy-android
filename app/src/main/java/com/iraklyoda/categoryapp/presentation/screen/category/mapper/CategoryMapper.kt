package com.iraklyoda.categoryapp.presentation.screen.category.mapper

import com.iraklyoda.categoryapp.domain.model.GetCategory
import com.iraklyoda.categoryapp.presentation.screen.category.model.CategoryUi

fun GetCategory.toUi(): CategoryUi {
    return CategoryUi(
        id = id,
        name = name,
        categoryLevel = categoryLevel
    )
}
