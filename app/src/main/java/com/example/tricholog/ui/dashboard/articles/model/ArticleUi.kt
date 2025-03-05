package com.example.tricholog.ui.dashboard.articles.model

data class ArticleUi(
    val id: String,
    val createdAt: String,
    val title: String,
    val content: String,
    val author: String,
    val summary: String,
    val readTimeMin: Int
)