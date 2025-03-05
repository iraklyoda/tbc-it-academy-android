package com.example.tricholog.domain.repository

import com.example.tricholog.domain.common.Resource
import com.example.tricholog.domain.error.ApiError
import com.example.tricholog.domain.model.TrichoLog
import kotlinx.coroutines.flow.Flow

interface TrichoLogRepository {
    suspend fun saveTrichoLog(trichoLog: TrichoLog): Flow<Resource<Boolean, ApiError>>

    suspend fun getTrichoLogs(): Flow<Resource<List<TrichoLog>, ApiError>>
}