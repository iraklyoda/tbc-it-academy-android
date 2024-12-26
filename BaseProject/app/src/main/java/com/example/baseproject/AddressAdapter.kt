package com.example.baseproject

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.ItemAddressBinding
import com.example.baseproject.models.Address


class AddressesDiffUtil : DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem == newItem
    }
}

class AddressAdapter : ListAdapter<Address, AddressAdapter.AddressViewHolder>(AddressesDiffUtil()) {

    var itemClickListener: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding: ItemAddressBinding =
            ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.itemClickListener = itemClickListener
        val address = getItem(position)

        holder.onBind(address)

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            address.isSelected = isChecked
            holder.onBind(address)
        }
    }

    inner class AddressViewHolder(private val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            address: Address
        ) {
            binding.imgAddressIcon.setImageResource(address.addressType.icon)
            binding.textAddress.text = address.address
            binding.textAddressName.text = address.addressName

            val color = if (address.isSelected) {
                binding.btnEdit.isEnabled = true
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.red
                )
            } else {
                binding.btnEdit.isEnabled = false
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.black
                )
            }

            binding.btnEdit.setTextColor(color)

            binding.btnEdit.setOnClickListener {
                address.isSelected = false
                itemClickListener?.invoke(adapterPosition)
            }
        }
        var itemClickListener: ((position: Int) -> Unit)? = null
        val checkBox: CheckBox = binding.checkboxAddress
    }

}