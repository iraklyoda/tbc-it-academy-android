package com.example.baseproject.models

import java.util.UUID

data class Address(
    val id: UUID = UUID.randomUUID(),
    var addressName: String,
    var address: String,
    var addressType: AddressType,
    var isSelected: Boolean = false
)