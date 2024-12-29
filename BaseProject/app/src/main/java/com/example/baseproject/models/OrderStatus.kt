package com.example.baseproject.models

data class OrderStatus(
    val status: String,
    val color: Int,
    var isSelected: Boolean
) {
    fun copy(isSelected: Boolean = this.isSelected) = OrderStatus(status, color, isSelected)
}