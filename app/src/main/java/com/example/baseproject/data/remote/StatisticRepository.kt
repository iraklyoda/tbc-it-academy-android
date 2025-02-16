package com.example.baseproject.data.remote

import com.example.baseproject.common.ApiHelper
import com.example.baseproject.common.Resource
import com.example.baseproject.data.dto.StatisticDto
import com.example.baseproject.data.remote.api.StatisticsService
import com.example.baseproject.domain.model.Statistic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class StatisticRepository @Inject constructor(
    private val statisticsService: StatisticsService,
    private val apiHelper: ApiHelper
) {

    suspend fun fetchStatistics(): Flow<Resource<List<Statistic>>> {
        return apiHelper.handleHttpRequest<List<StatisticDto>> {
            statisticsService.getStatistics()
        }.map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val response = resource.data
                    val statisticsList = response.map { it.toStatistic() }
                    Resource.Success(statisticsList)
                }

                is Resource.Error -> Resource.Error(resource.errorMessage)
                is Resource.Loading -> Resource.Loading(resource.loading)
            }
        }
    }
}