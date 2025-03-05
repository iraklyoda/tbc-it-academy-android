package com.example.tricholog.data.mapper

import com.example.tricholog.data.model.TrichoLogDto
import com.example.tricholog.domain.model.TrichoLog

fun TrichoLog.toFirebaseMap(): Map<String, Any> {
    return mapOf(
        "createdAt" to this.createdAt,
        "trigger" to this.createdAt,
        "body" to this.body
    )
}

fun TrichoLogDto.toTrichoLogDomain(): TrichoLog {
    return TrichoLog(
        id, createdAt, trigger, body
    )
}