package com.iraklyoda.categoryapp.data.mapper

import com.iraklyoda.categoryapp.data.remote.dto.CategoryDto
import com.iraklyoda.categoryapp.domain.model.GetCategory

fun CategoryDto.toDomain(): GetCategory {
    return GetCategory(
        id = id,
        name = name,
        nameDe = nameDe,
        createdAt = createdAt,
        bglNumber = bglNumber,
        bglVariant = bglVariant,
        orderId = orderId,
        children = children?.map { it.toDomain() } ?: emptyList()
    )
}