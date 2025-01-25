package com.example.baseproject.user

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.baseproject.R
import com.example.baseproject.databinding.ItemUserBinding

class UserDiffUtil : DiffUtil.ItemCallback<UserDto>() {
    override fun areItemsTheSame(oldItem: UserDto, newItem: UserDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserDto, newItem: UserDto): Boolean {
        return oldItem == newItem
    }

}

class UsersAdapter : ListAdapter<UserDto, UsersAdapter.UserViewHolder>(UserDiffUtil()) {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(
            firstName: String,
            lastName: String,
            email: String,
            avatar: String
        ) {
            binding.apply {
                textFullName.text = "$firstName $lastName"
                textEmail.text = email

                Glide.with(ivProfile.context).load(avatar).apply(
                    RequestOptions().placeholder(R.drawable.ic_launcher_background)
                        .transform(CircleCrop())
                ).into(ivProfile)
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

        holder.onBind(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            avatar = user.avatar
        )
    }
}