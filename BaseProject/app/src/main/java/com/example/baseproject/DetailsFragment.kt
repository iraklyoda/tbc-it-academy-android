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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

    fun getSelectedStatus(): String {
        return if (binding.rbDelivered.isChecked) {
            "DELIVERED"
        } else {
            "CANCELED"
        }
    }
}