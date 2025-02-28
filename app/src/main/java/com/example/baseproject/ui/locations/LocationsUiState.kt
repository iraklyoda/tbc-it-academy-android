package com.example.baseproject.ui.locations

import com.example.baseproject.domain.model.Location

sealed class LocationsUiState {
    data object Idle: LocationsUiState()
    data object Loading: LocationsUiState()
    data class Success(val locations: List<Location>): LocationsUiState()
    data class Error(val error: String): LocationsUiState()
}

