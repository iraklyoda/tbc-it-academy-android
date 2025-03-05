package com.example.tricholog.domain.model

import java.util.UUID

data class TrichoLog(
    var id: String = UUID.randomUUID().toString(),
    val createdAt: Long,
    val trigger: String,
    val body: String,
)