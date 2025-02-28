package com.example.tricholog.data.mapper

import com.example.tricholog.data.remote.dto.ArticleDto
import com.example.tricholog.domain.model.Article

fun ArticleDto.toArticleDomain(): Article {
    return Article(
        id, createdAt, title, content, author, summary, readTimeMin
    )
}