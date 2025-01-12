package com.example.baseproject.ordersui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baseproject.orders.Order
import com.example.baseproject.orders.OrderStatus
import com.example.baseproject.ordersui.OrdersFragment.Companion

class OrderStatusAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val orderList: ArrayList<Order> = arrayListOf()
): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val activeOrdersList = ArrayList(orderList.filter { it.status == OrderStatus.ACTIVE })
        val completedOrdersList = ArrayList(orderList.filter { it.status == OrderStatus.COMPLETED })

        return when (position) {
            0 -> ActiveOrdersFragment().apply {
                val bundle = Bundle().apply {
                    putParcelableArrayList("OrdersList", activeOrdersList)
                }
                arguments = bundle
            }
            1 -> CompletedOrdersFragment().apply {
                val bundle = Bundle().apply {
                    putParcelableArrayList("OrdersList", completedOrdersList)
                }
                arguments = bundle
            }
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}