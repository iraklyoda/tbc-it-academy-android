package com.example.baseproject.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.baseproject.R
import com.example.baseproject.data.remote.dto.UserDto
import com.example.baseproject.databinding.ItemUserBinding

class UserDiffUtil : DiffUtil.ItemCallback<UserDto>() {
    override fun areItemsTheSame(oldItem: UserDto, newItem: UserDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserDto, newItem: UserDto): Boolean {
        return oldItem == newItem
    }

}

class UsersAdapter : PagingDataAdapter<UserDto, UsersAdapter.UserViewHolder>(UserDiffUtil()) {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(
            user: UserDto?,
        ) {
            user?.let {
                binding.apply {
                    textFullName.text = "${it.firstName} ${it.lastName}"
                    textEmail.text = it.email

                    Glide.with(ivProfile.context).load(it.avatar).apply(
                        RequestOptions().placeholder(R.drawable.ic_launcher_background)
                            .transform(CircleCrop())
                    ).into(ivProfile)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: ItemUserBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.onBind(user)
    }
}