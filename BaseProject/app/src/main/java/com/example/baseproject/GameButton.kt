package com.example.baseproject

import java.util.UUID

data class GameButton(
    val id: UUID = UUID.randomUUID(),
    var state: CellState = CellState.EMPTY
)