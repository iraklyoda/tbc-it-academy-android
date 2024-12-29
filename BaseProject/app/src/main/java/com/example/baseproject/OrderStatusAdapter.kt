package com.example.baseproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.models.OrderStatus
import com.example.baseproject.databinding.ItemOrderStatusBinding


class OrderStatusesDiffUtil : DiffUtil.ItemCallback<OrderStatus>() {
    override fun areItemsTheSame(oldItem: OrderStatus, newItem: OrderStatus): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: OrderStatus, newItem: OrderStatus): Boolean {
        return oldItem == newItem
    }
}

class OrderStatusAdapter :
    ListAdapter<OrderStatus, OrderStatusAdapter.OrderStatusViewHolder>(OrderStatusesDiffUtil()) {

    var itemClickListener: ((position: Int) -> Unit)? = null

    inner class OrderStatusViewHolder(private val binding: ItemOrderStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var itemClickListener: ((position: Int) -> Unit)? = null

        fun onBind(
            position: Int,
            isSelected: Boolean,
            status: String,
        ) {
            binding.apply {
                textStatus.text = status

                if (isSelected) {
                    textStatus.setBackgroundResource(R.drawable.bg_status)
                    textStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.white
                        )
                    )
                } else {
                    textStatus.background = null
                    textStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.black
                        )
                    )
                }
            }

            binding.textStatus.setOnClickListener {
                itemClickListener?.invoke(position)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderStatusViewHolder {
        val binding: ItemOrderStatusBinding =
            ItemOrderStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderStatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderStatusViewHolder, position: Int) {
        holder.itemClickListener = itemClickListener

        holder.onBind(
            position = position,
            isSelected = getItem(position).isSelected,
            status = getItem(position).status,
        )
    }

}