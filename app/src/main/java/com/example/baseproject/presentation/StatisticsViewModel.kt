package com.example.baseproject.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.common.Resource
import com.example.baseproject.data.remote.StatisticRepository
import com.example.baseproject.domain.model.Statistic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticRepository: StatisticRepository
) : ViewModel() {

    private val _statisticsStateFlow: MutableStateFlow<Resource<List<Statistic>>> =
        MutableStateFlow(Resource.Loading(false))

    val statisticsStateFlow: StateFlow<Resource<List<Statistic>>> get() = _statisticsStateFlow


    init {
        viewModelScope.launch {
            statisticRepository.fetchStatistics().collectLatest { resource ->
                _statisticsStateFlow.emit(resource)
            }
        }
    }
}