package com.example.baseproject.ordersui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentActiveOrdersBinding
import com.example.baseproject.orders.Order
import com.example.baseproject.orders.OrderAdapter
import com.example.baseproject.orders.OrderStatus

class ActiveOrdersFragment : Fragment() {
    private var _binding: FragmentActiveOrdersBinding? = null
    private val binding: FragmentActiveOrdersBinding get() = _binding!!


    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orders: ArrayList<Order> = arguments?.getParcelableArrayList("OrdersList") ?: ArrayList()
        ordersList = orders
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActiveOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOrders()
    }

    private fun setOrders() {
        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrders.adapter = orderAdapter
        orderAdapter.submitList(ordersList)
    }

    companion object {
        var ordersList: ArrayList<Order> = arrayListOf<Order>(
        )
    }
}