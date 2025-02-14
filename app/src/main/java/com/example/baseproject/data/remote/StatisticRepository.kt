package com.example.baseproject.data.remote

import com.example.baseproject.data.dto.StatisticDto
import com.example.baseproject.data.remote.api.StatisticsService
import com.example.baseproject.domain.model.Statistic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class StatisticRepository @Inject constructor(
    private val statisticsService: StatisticsService,
) {

    private suspend fun fetchStatistics(): List<StatisticDto> {
        return statisticsService.getStatistics()
    }

    fun getStatisticsFlow(): Flow<List<Statistic>> = flow {
        emit(fetchStatistics().map { it.toStatistic() })
    }
}