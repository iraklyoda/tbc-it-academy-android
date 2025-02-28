package com.example.baseproject.data.mapper

import com.example.baseproject.data.remote.dto.LocationDto
import com.example.baseproject.domain.model.Location

fun LocationDto.toLocationDomain(): Location {
    return Location(
        lat, lan, title, address
    )
}