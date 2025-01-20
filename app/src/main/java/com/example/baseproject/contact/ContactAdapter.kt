package com.example.baseproject.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.baseproject.R
import com.example.baseproject.databinding.ItemContactBinding

class ContactDiffUtil : DiffUtil.ItemCallback<ContactPto>() {
    override fun areItemsTheSame(oldItem: ContactPto, newItem: ContactPto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContactPto, newItem: ContactPto): Boolean {
        return oldItem == newItem
    }

}

class ContactAdapter :
    ListAdapter<ContactPto, ContactAdapter.ContactViewHolder>(ContactDiffUtil()) {

    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(contact: ContactPto) {
            binding.apply {
                txtFullName.text = contact.owner
                txtMessage.text = contact.lastMessage
                txtMsgAmount.text = contact.unreadMessages.toString()

                Glide.with(ivProfile.context).load(contact.image).apply(
                    RequestOptions().placeholder(R.drawable.profile)
                        .transform(CircleCrop())
                ).into(ivProfile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding: ItemContactBinding =
            ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.onBind(contact = getItem(position))
    }

}