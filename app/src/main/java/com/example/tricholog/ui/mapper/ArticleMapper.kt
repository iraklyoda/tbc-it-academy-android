package com.example.tricholog.ui.mapper

import com.example.tricholog.domain.common.DateHelper
import com.example.tricholog.domain.model.Article
import com.example.tricholog.ui.dashboard.articles.model.ArticleUi

fun Article.toArticleUi(): ArticleUi {
    return ArticleUi(
        id, createdAt = DateHelper.convertsMillisToDate(this.createdAt), title, content, author, summary, readTimeMin
    )
}
