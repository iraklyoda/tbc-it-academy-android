package com.iraklyoda.categoryapp.presentation.screen.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iraklyoda.categoryapp.databinding.ItemCategoryLevelBinding

class CategoryLevelDiffUtil : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

}

class CategoryLevelAdapter :
    ListAdapter<Int, CategoryLevelAdapter.CategoryLevelViewHolder>(CategoryLevelDiffUtil()) {
    inner class CategoryLevelViewHolder(private val binding: ItemCategoryLevelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryLevelViewHolder {
        val binding: ItemCategoryLevelBinding =
            ItemCategoryLevelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryLevelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryLevelViewHolder, position: Int) {
        holder.onBind()
    }
}