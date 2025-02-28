package com.example.baseproject.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    @SerialName("lat") val lat: Double,
    @SerialName("lan") val lan: Double,
    @SerialName("title") val title: String,
    @SerialName("address") val address: String
)
