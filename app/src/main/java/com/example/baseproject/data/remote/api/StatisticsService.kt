package com.example.baseproject.data.remote.api

import com.example.baseproject.data.dto.StatisticDto
import retrofit2.Response
import retrofit2.http.GET

interface StatisticsService {
    @GET("6dffd14a-836f-4566-b024-bd41ace3a874")
    suspend fun getStatistics(): Response<List<StatisticDto>>
}