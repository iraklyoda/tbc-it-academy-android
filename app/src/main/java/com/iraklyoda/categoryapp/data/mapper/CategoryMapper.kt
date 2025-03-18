package com.iraklyoda.categoryapp.data.mapper

import com.iraklyoda.categoryapp.data.remote.dto.CategoryDto
import com.iraklyoda.categoryapp.domain.model.GetCategory

fun List<CategoryDto>.toDomain(): List<GetCategory> {
    fun flatten(categories: List<CategoryDto>, categoryLevel: Int = 0): List<GetCategory> {
        return categories.flatMap { category ->

            val currentCategory = GetCategory(
                id = category.id,
                name = category.name,
                nameDe = category.nameDe,
                createdAt = category.createdAt,
                bglNumber = category.bglNumber,
                bglVariant = category.bglVariant,
                orderId = category.orderId,
                categoryLevel = categoryLevel
            )

            listOf(currentCategory) + flatten(
                categories = category.children ?: emptyList(),
                categoryLevel = categoryLevel + 1
            )
        }
    }

    return flatten(categories = this)
}
