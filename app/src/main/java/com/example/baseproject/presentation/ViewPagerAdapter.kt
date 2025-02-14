package com.example.baseproject.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.baseproject.R
import com.example.baseproject.databinding.ItemStatisticBinding
import com.example.baseproject.domain.model.Statistic

class StatisticDiffUtil : DiffUtil.ItemCallback<Statistic>() {
    override fun areItemsTheSame(oldItem: Statistic, newItem: Statistic): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Statistic, newItem: Statistic): Boolean {
        return oldItem == newItem
    }
}

class ViewPagerAdapter :
    ListAdapter<Statistic, ViewPagerAdapter.StatisticViewHolder>(StatisticDiffUtil()) {

    inner class StatisticViewHolder(private val binding: ItemStatisticBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(statistic: Statistic?) {
            statistic?.let {
                binding.apply {
                    tvPrice.text = it.price
                    tvLocation.text = it.location
                    tvReactionCount.text = it.reactionCount.toString()
                    tvTitle.text = it.title
                    rb.rating = it.rate.toFloat()


                    Glide.with(ivBackground.context).load(it.cover).apply(
                        RequestOptions().placeholder(R.drawable.ic_launcher_background)
                    ).into(ivBackground)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticViewHolder {
        val binding: ItemStatisticBinding = ItemStatisticBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StatisticViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatisticViewHolder, position: Int) {
        val statistic = getItem(position)
        holder.onBind(statistic)
    }
}