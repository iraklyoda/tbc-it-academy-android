package com.example.baseproject.data.dto

import com.example.baseproject.domain.model.Statistic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatisticDto(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val location: String,
    @SerialName("reaction_count") val reactionCount: Int,
    val rate: Int?
) {
    fun toStatistic(): Statistic {
        return Statistic(
            id = id,
            cover = cover,
            price = price,
            title = title,
            location = location,
            reactionCount = reactionCount,
            rate = rate ?: 0
        )
    }
}