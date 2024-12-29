package com.example.baseproject.models

import kotlin.random.Random

object OrderData {
    private var orderId = 0

    private val trackingIds: MutableSet<String> = mutableSetOf()

    val ordersList = listOf(
        Order(
            orderID = ++orderId,
            trackingID = generateTrackingId(),
            subtotal = 200,
            date = System.currentTimeMillis(),
            quantity = 1,
        ),
        Order(
            orderID = ++orderId,
            trackingID = generateTrackingId(),
            subtotal = 150,
            date = System.currentTimeMillis(),
            quantity = 1,
            status = OrderStatusData.getStatus("CANCELED")
        ),
        Order(
            orderID = ++orderId,
            trackingID = generateTrackingId(),
            subtotal = 420,
            date = System.currentTimeMillis(),
            quantity = 1,
            status = OrderStatusData.getStatus("DELIVERED")
        ),
        Order(
            subtotal = 2400,
            orderID = ++orderId,
            trackingID = generateTrackingId(),
            date = System.currentTimeMillis(),
            quantity = 8,
            status = OrderStatusData.getStatus("DELIVERED")
        ),
        Order(
            orderID = ++orderId,
            subtotal = 2400,
            trackingID = generateTrackingId(),
            date = System.currentTimeMillis(),
            quantity = 8,
            status = OrderStatusData.getStatus("DELIVERED")
        ),
        Order(
            orderID = ++orderId,
            subtotal = 386,
            trackingID = generateTrackingId(),
            date = System.currentTimeMillis(),
            quantity = 2,
        ),
        Order(
            orderID = ++orderId,
            subtotal = 400,
            trackingID = generateTrackingId(),
            date = System.currentTimeMillis(),
            quantity = 8,
        ),
    )

    private fun generateTrackingId(): String {
        val id = (1..9).map {
            Random.nextInt(0, 10)
        }.joinToString("")

        if (!trackingIds.contains(id)) {
            trackingIds.add(id)
            return "TK$id"
        } else {
            return generateTrackingId()
        }
    }

    private fun getOrders(): List<Order> {
        return ordersList
    }

    fun getOrder(orderId: Int?): Order? {
        return getOrders().find { it.orderID == orderId }
    }

    fun updateOrderStatus(orderId: Int?, status: OrderStatus) {
        val index = getOrders().indexOfFirst { it.orderID == orderId }
        getOrders()[index].status = status
    }

    fun getFilteredOrders(orderStatus: OrderStatus): List<Order> {
        return ordersList.filter { it.status == orderStatus }
    }
}