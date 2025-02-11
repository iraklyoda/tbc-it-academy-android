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
import com.example.baseproject.domain.model.User

class UserDiffUtil : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}

class UsersAdapter : PagingDataAdapter<User, UsersAdapter.UserViewHolder>(UserDiffUtil()) {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            user: User?,
        ) {
            user?.let {
                binding.apply {
                    textFullName.text = it.fullName
                    textEmail.text = it.email

                    Glide.with(ivProfile.context).load(it.avatarUrl).apply(
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