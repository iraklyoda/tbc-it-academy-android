package com.example.baseproject.ordersui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentOrdersBinding
import com.example.baseproject.orders.Order
import com.example.baseproject.orders.OrderStatus
import com.example.baseproject.orders.OrderStatusAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OrdersFragment : Fragment() {
    private var _binding: FragmentOrdersBinding? = null
    private val binding: FragmentOrdersBinding get() = _binding!!

    private lateinit var adapter: FragmentStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabs()
    }

    private fun setUpTabs() {

        adapter = OrderStatusAdapter(
            parentFragmentManager,
            lifecycle,
            orderList
        )
        binding.vp2Products.adapter = adapter

        TabLayoutMediator(binding.tlStatus, binding.vp2Products) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.active_orders)
                1 -> tab.text = getString(R.string.completed_orders)
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val orderList: ArrayList<Order> = arrayListOf(
            Order(
                name = "Lawson Chair",
                image = R.drawable.chair_001,
                colorName = "Brown",
                color = R.color.oak_brown,
                quantity = 1,
                price = 120.00,
                status = OrderStatus.ACTIVE
            ),
            Order(
                name = "Lawson Chair",
                image = R.drawable.chair_001,
                colorName = "Brown",
                color = R.color.oak_brown,
                quantity = 1,
                price = 120.00,
                status = OrderStatus.ACTIVE
            ),
            Order(
                name = "Wooden Stenka",
                image = R.drawable.wardrobe_001,
                colorName = "Brown",
                color = R.color.oak_brown,
                quantity = 1,
                price = 200.00,
                status = OrderStatus.COMPLETED
            ),
            Order(
                name = "Cool Lamp",
                image = R.drawable.lamp_001,
                colorName = "Yellow",
                color = R.color.immortelle_yellow,
                quantity = 3,
                price = 40.00,
                status = OrderStatus.COMPLETED
            ),
            Order(
                name = "Sofa",
                image = R.drawable.sofa,
                colorName = "Light Blue",
                color = R.color.light_blue,
                quantity = 2,
                price = 240.20,
                status = OrderStatus.COMPLETED
            ),
            Order(
                name = "Cooler Lamp",
                image = R.drawable.lamp_001,
                colorName = "Yellow",
                color = R.color.immortelle_yellow,
                quantity = 1,
                price = 400.00,
                status = OrderStatus.COMPLETED
            ),
        )
    }
}