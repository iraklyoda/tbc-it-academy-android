package com.example.baseproject.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.baseproject.R
import com.example.baseproject.data.UserEntity
import com.example.baseproject.databinding.UserItemBinding

class UsersDiffUtil : DiffUtil.ItemCallback<UserEntity>() {
    override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem == newItem
    }

}

class UsersAdapter : ListAdapter<UserEntity, UsersAdapter.UsersViewHolder>(UsersDiffUtil()) {

    inner class UsersViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: UserEntity) {
            binding.apply {
                tvFullName.text = "${item.firstName} ${item.lastName}"
                tvAbout.text = "${item.about}"

                tvActivation.text = when (item.status) {
                    ActivationStatus.INACTIVE -> itemView.context.getString(R.string.user_is_inactive)
                    ActivationStatus.ONLINE -> itemView.context.getString(R.string.user_is_online)
                    ActivationStatus.RECENT -> itemView.context.getString(R.string.active_few_minutes_ago)
                    ActivationStatus.TODAY -> itemView.context.getString(R.string.active_few_hours_ago)
                    ActivationStatus.GONE -> itemView.context.getString(R.string.user_hasn_t_been_online_for_centuries)
                }

                tvActivation.setTextColor(ContextCompat.getColor(itemView.context, item.status.statusColor))

                Glide.with(itemView.context)
                    .load(item.avatar)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.image_error)
                    .into(ivProfile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding: UserItemBinding =
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}