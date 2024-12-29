package com.example.baseproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.ItemOrderBinding
import com.example.baseproject.models.Order
import com.example.baseproject.models.OrderStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderDiffUtil : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.orderID == newItem.orderID
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}

class OrderAdapter :
    ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffUtil()) {

    var detailsClickListener: ((orderId: Int) -> Unit)? = null

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var detailsClickListener: ((position: Int) -> Unit)? = null

        fun onBind(
            orderId: Int,
            trackingId: String,
            subtotal: Int,
            quantity: Int,
            date: String,
            status: OrderStatus
        ) {
            with(binding) {
                val color: Int = ContextCompat.getColor(
                    root.context, status.color
                )

                textOrderStatus.text = status.status
                textOrderStatus.setTextColor(color)

                textHeader.text = "Order #$orderId"
                textSubtotalAmount.text = "$subtotal$"
                textDate.text = date
                textTrackingNumber.text = trackingId
                textQuantityNumber.text = quantity.toString()

                btnDetails.setOnClickListener {
                    detailsClickListener?.invoke(orderId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding: ItemOrderBinding =
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.detailsClickListener = detailsClickListener
        val item: Order = getItem(position)

        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val time: String = formatter.format(Date(item.date))

        holder.onBind(
            orderId = item.orderID,
            date = time,
            subtotal = item.subtotal,
            trackingId = item.trackingID,
            status = item.status,
            quantity = item.quantity
        )
    }
}