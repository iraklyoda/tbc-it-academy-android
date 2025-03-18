package com.iraklyoda.categoryapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("name_de") val nameDe: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("bgl_number") val bglNumber: String?,
    @SerialName("bgl_variant") val bglVariant: String?,
    @SerialName("order_id") val orderId: Int?,
    @SerialName("children") val children: List<CategoryDto>?
)