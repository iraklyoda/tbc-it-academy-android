package com.example.baseproject.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    @SerialName("id") val id: Int,
    @SerialName("cover") val cover: String,
    @SerialName("title") val title: String
)
