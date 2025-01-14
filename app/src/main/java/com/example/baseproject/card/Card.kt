package com.example.baseproject.card

import java.util.UUID

data class Card(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val number: Long,
    val expireMonth: Int,
    val expireYear: Int,
    val cvv: Int,
    val cardType: CardType
)