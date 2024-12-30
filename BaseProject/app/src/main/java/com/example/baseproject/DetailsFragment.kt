package com.example.baseproject

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import com.example.baseproject.databinding.FragmentDetailsBinding
import com.example.baseproject.models.Order
import com.example.baseproject.models.OrderData
import com.example.baseproject.models.OrderStatus
import com.example.baseproject.models.OrderStatusData

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val orderId = arguments?.getInt("orderId")
        val order: Order? = OrderData.getOrder(orderId)

        order?.let  {
            with(binding) {

                if(order.status.status != "PENDING") {
                    rgStatus.visibility = View.GONE
                }

                textOrderNumber.text =
                    "Order Id: ${order.orderID}"
                textTrackingNumber.text =
                    "Tracking Id: ${order.trackingID}"
                textStatusCurrent.text =
                    order.status.status

               textStatusCurrent.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        order.status.color
                    )
                )

                rgStatus.setOnCheckedChangeListener {RadioGroup, Unit ->
                    btnSubmit.visibility = View.VISIBLE
                }

                btnSubmit.setOnClickListener {
                    OrderData.updateOrderStatus(orderId, OrderStatusData.getStatus(getSelectedStatus()))
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

        }

        d("FragmentDetail", "position is $orderId")
        return binding.root
    }

    private fun getSelectedStatus(): String {
        return if (binding.rbDelivered.isChecked) {
            "DELIVERED"
        } else {
            "CANCELED"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}