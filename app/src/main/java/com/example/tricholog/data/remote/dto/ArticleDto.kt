package com.example.tricholog.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    @SerialName("id") val id: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("author") val author: String,
    @SerialName("summary") val summary: String,
    @SerialName("read_time_min") val readTimeMin: Int
)