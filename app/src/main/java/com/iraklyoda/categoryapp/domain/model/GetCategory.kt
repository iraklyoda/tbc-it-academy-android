package com.iraklyoda.categoryapp.domain.model

data class GetCategory(
    val id: String,
    val name: String,
    val nameDe: String,
    val createdAt: String,
    val bglNumber: String?,
    val bglVariant: String?,
    val orderId: Int?,
    val categoryLevel: Int,
)
