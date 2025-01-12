package com.example.baseproject.ordersui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentCompletedOrdersBinding
import com.example.baseproject.orders.Order
import com.example.baseproject.orders.OrderAdapter
import com.example.baseproject.orders.OrderStatus
import com.example.baseproject.ordersui.ActiveOrdersFragment.Companion

class CompletedOrdersFragment : Fragment() {
    private var _binding: FragmentCompletedOrdersBinding? = null
    private val binding: FragmentCompletedOrdersBinding get() = _binding!!

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter { position ->
            openReviewDrawer(position)
        }
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
        _binding = FragmentCompletedOrdersBinding.inflate(inflater, container, false)
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

    private fun openReviewDrawer(position: Int) {
        val reviewBottomSheet = ReviewOrderFragment()
        val bundle: Bundle = Bundle().apply {
            putParcelable("order_key", ordersList[position])
        }
        reviewBottomSheet.arguments = bundle
        reviewBottomSheet.show(parentFragmentManager, reviewBottomSheet.tag)
    }

    companion object {
        var ordersList: ArrayList<Order> = arrayListOf<Order>(
        )
    }
}