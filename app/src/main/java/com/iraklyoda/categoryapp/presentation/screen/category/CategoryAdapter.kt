package com.iraklyoda.categoryapp.presentation.screen.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iraklyoda.categoryapp.databinding.ItemCategoryBinding
import com.iraklyoda.categoryapp.presentation.screen.category.model.CategoryUi

class CategoryDiffUtil : DiffUtil.ItemCallback<CategoryUi>() {
    override fun areItemsTheSame(oldItem: CategoryUi, newItem: CategoryUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryUi, newItem: CategoryUi): Boolean {
        return oldItem == newItem
    }
}

class CategoryAdapter :
    ListAdapter<CategoryUi, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtil()) {

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

            private val categoryLevelAdapter by lazy {
                CategoryLevelAdapter()
            }
            fun onBind(category: CategoryUi) {
                val parentCount: List<Int> = List(category.parentCount.coerceAtMost(4)) {0}

                binding.apply {
                    tvTitle.text = category.name
                    rvLevel.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                    rvLevel.adapter = categoryLevelAdapter
                    rvLevel.itemAnimator = null
                    categoryLevelAdapter.submitList(parentCount)
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding: ItemCategoryBinding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.onBind(category = category)
    }
}