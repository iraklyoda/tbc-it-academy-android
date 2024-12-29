package com.example.baseproject.models

import com.example.baseproject.R

object OrderStatusData {

    private val statuses: Map<String, OrderStatus> = mapOf(
        "PENDING" to OrderStatus(
            status = "PENDING",
            color = R.color.alloy_orange,
            isSelected = true
        ),
        "DELIVERED" to OrderStatus(
            status = "DELIVERED",
            color = R.color.spanish_green,
            isSelected = false
        ),
        "CANCELED" to OrderStatus(
            status = "CANCELED",
            color = R.color.boston_university_red,
            isSelected = false
        ),
    )

    fun getStatus(status: String): OrderStatus {
        val currentStatus: OrderStatus? = statuses[status]
        currentStatus?.let {
            return currentStatus
        }
        return OrderStatus(status = "PENDING", color = R.color.alloy_orange, isSelected = true)
    }

    fun getStatusesList(): List<OrderStatus> {
        return statuses.values.toList()
    }
}