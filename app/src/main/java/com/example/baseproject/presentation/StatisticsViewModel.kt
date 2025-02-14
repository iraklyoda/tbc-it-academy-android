package com.example.baseproject.presentation

import androidx.lifecycle.ViewModel
import com.example.baseproject.data.remote.StatisticRepository
import com.example.baseproject.domain.model.Statistic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticRepository: StatisticRepository
) : ViewModel() {

    val statistics: Flow<List<Statistic>> = statisticRepository.getStatisticsFlow()
}