package com.example.baseproject.orders

import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.ItemOrderBinding

class OrderDiffUtil : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}

class OrderAdapter(private val onClick: (position: Int) -> Unit = {}) :
    ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffUtil()) {

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            name: String,
            image: Int,
            quantity: Int,
            color: Int,
            colorName: String,
            status: OrderStatus,
            price: Double
        ) {
            binding.apply {
                val itemColor: Int = ContextCompat.getColor(itemView.context, color)

                txtOrderName.text = name
                txtColor.text = colorName
                ivColor.background.setTint(itemColor)
                ivOrder.setImageResource(image)
                txtStatus.text = status.status
                txtQtyAmount.text = quantity.toString()
                btnAction.text = status.action
                txtPrice.text = DecimalFormat("#.00").format(price)
                btnAction.setOnClickListener { onClick.invoke(adapterPosition) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding: ItemOrderBinding =
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.onBind(
            name = order.name,
            image = order.image,
            color = order.color,
            colorName = order.colorName,
            status = order.status,
            quantity = order.quantity,
            price = order.price
        )
    }
}