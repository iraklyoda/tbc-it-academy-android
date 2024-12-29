package com.example.baseproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.models.OrderStatus
import com.example.baseproject.databinding.FragmentOrdersBinding
import com.example.baseproject.models.Order
import com.example.baseproject.models.OrderData
import com.example.baseproject.models.OrderStatusData

class OrdersFragment : Fragment() {
    private var _binding: FragmentOrdersBinding? = null
    private val binding: FragmentOrdersBinding get() = _binding!!
    private var currentStatus: OrderStatus = OrderStatusData.getStatus("PENDING")

    private val orderStatusAdapter: OrderStatusAdapter by lazy {
        OrderStatusAdapter()
    }
    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)

        currentStatus = OrderStatusData.getStatus("PENDING")
        setUp()

        return binding.root
    }

    private fun setUp() {
        setStatuses()
        setOrders()
    }

    private fun setStatuses() {
        val orderStatuses: List<OrderStatus> = OrderStatusData.getStatusesList()

        binding.rvStatuses.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvStatuses.adapter = orderStatusAdapter
        orderStatusAdapter.submitList(orderStatuses)

        orderStatusAdapter.itemClickListener = { position: Int ->
            val updatedList: List<OrderStatus> = orderStatuses.mapIndexed { index, singleStatus ->
                singleStatus.copy(isSelected = index == position)
            }
            currentStatus = orderStatuses[position]
            orderStatusAdapter.submitList(updatedList)
            orderAdapter.submitList(OrderData.getFilteredOrders(currentStatus).toList())
        }
    }

    private fun setOrders() {
        val orders: List<Order> = OrderData.getFilteredOrders(currentStatus)
        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrders.adapter = orderAdapter

        orderAdapter.detailsClickListener = { orderId: Int ->
            val fragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putInt("orderId", orderId)
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit()
        }

        orderAdapter.submitList(orders)
    }
}