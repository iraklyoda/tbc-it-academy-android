package com.example.baseproject.ui.home.location

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.baseproject.R
import com.example.baseproject.databinding.ItemLocationBinding

class LocationDiffUtil : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }
}

class LocationAdapter :
    ListAdapter<Location, LocationAdapter.LocationViewHolder>(LocationDiffUtil()) {
    inner class LocationViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(location: Location) {
            binding.apply {
                tvTitle.text = location.title

                Glide.with(ivLocation.context)
                    .load(location.cover)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(ivLocation)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding: ItemLocationBinding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    private inner class HorizontalSpacingDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            val itemCount = state.itemCount

            if (position == 0) {
                outRect.left = parent.context.resources.getDimensionPixelSize(R.dimen.margin_horizontal_md)
            } else {
                outRect.left = parent.context.resources.getDimensionPixelSize(R.dimen.margin_horizontal_sm)
            }

            if (position == itemCount - 1) {
                outRect.right = parent.context.resources.getDimensionPixelSize(R.dimen.margin_horizontal_md)
            }
        }
    }

    fun attachItemDecoration(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(HorizontalSpacingDecoration())
    }
}