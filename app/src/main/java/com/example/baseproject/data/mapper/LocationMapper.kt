package com.example.baseproject.data.mapper

import com.example.baseproject.data.remote.dto.LocationDto
import com.example.baseproject.ui.home.location.Location

fun LocationDto.toLocationUI(): Location {
    return Location(
        id = this.id,
        cover = this.cover,
        title = this.title
    )
}