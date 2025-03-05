package com.example.tricholog.data.model

data class TrichoLogDto(
    var id: String = "",
    val createdAt: Long = 0L,
    val trigger: String = "",
    val body: String = "",
)