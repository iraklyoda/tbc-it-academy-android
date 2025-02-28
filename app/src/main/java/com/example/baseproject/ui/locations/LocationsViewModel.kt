package com.example.baseproject.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.domain.common.Resource
import com.example.baseproject.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val locationRepository: LocationRepository
): ViewModel() {

    private val _locationsStateFlow: MutableStateFlow<LocationsUiState> = MutableStateFlow(LocationsUiState.Idle)
    val locationsStateFlow get() = _locationsStateFlow

    init {
        viewModelScope.launch {
            locationRepository.getLocations().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> _locationsStateFlow.value = LocationsUiState.Loading
                    is Resource.Success -> _locationsStateFlow.value = LocationsUiState.Success(resource.data)
                    is Resource.Error -> _locationsStateFlow.value = LocationsUiState.Error(resource.errorMessage)
                }
            }
        }
    }
}