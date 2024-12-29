package com.example.baseproject.models

import java.util.UUID

data class Order(
    val orderID: Int,
    val trackingID: String,
    val subtotal: Int,
    val quantity: Int,
    var date: Long = System.currentTimeMillis(),
    var status: OrderStatus = OrderStatusData.getStatus("PENDING")
) {

}