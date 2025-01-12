package com.example.baseproject.orders

enum class OrderStatus(val status: String, val action: String) {
    ACTIVE(status = "in Delivery", action = "Track Order"),
    COMPLETED(status = "Completed", action = "Leave Review")
}