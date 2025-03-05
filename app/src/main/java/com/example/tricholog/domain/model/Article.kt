package com.example.tricholog.domain.model

data class Article(
    val id: String,
    val createdAt: Long,
    val title: String,
    val content: String,
    val author: String,
    val summary: String,
    val readTimeMin: Int
)