package com.example.baseproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.ItemAddressBinding
import com.example.baseproject.models.Address
import com.example.baseproject.models.AddressType

class AddressAdapter(
    private var addresses: MutableList<Address>
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    inner class AddressViewHolder(private val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(addressName: String, address: String, addressType: AddressType) {
            binding.imgAddressIcon.setImageResource(addressType.icon)
            binding.textAddress.text = address
            binding.textAddressName.text = addressName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding: ItemAddressBinding =
            ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return addresses.size
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(
            addressName = addresses[position].address,
            address = addresses[position].address,
            addressType = addresses[position].addressType
        )
    }

}