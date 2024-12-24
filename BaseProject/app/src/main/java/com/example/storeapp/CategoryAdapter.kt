package com.example.storeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.databinding.ItemCategoryBinding
import com.example.storeapp.model.Category
import kotlin.enums.EnumEntries

class CategoryAdapter(
    private var categories: EnumEntries<Category>,
    private val onClick: (Category) -> Unit

) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    // Default position is always "All"
    private var selectedPosition: Int = 0

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, icon: String?) {

            // Set Icon
            if (icon == null) {
                binding.tvCategoryIcon.visibility = View.GONE
            } else {
                binding.tvCategoryIcon.visibility = View.VISIBLE
                binding.tvCategoryIcon.text = category.icon
            }

            binding.tvCategoryDisplay.text = category.displayName

            // Check if category is active and set corresponding visuals
            val color: Int
            val backgroundShape: Int

            if (category.isActive) {
                color = ContextCompat.getColor(binding.root.context, R.color.white)
                backgroundShape = R.drawable.shape_active_category_background
            } else {
                backgroundShape = R.drawable.shape_category_background
                color = ContextCompat.getColor(binding.root.context, R.color.cadet_gray)
            }

            binding.llCategoryItem.setBackgroundResource(backgroundShape)
            binding.tvCategoryDisplay.setTextColor(color)


            binding.root.setOnClickListener {
                // Get previous active category
                val oldPosition: Int = selectedPosition
                // Set current active
                selectedPosition = adapterPosition

                category.setActive()

                // Update both category items
                notifyItemChanged(oldPosition)
                notifyItemChanged(selectedPosition)

                // return category on click
                onClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding: ItemCategoryBinding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(
            category = categories[position],
            icon = categories[position].icon
        )
    }
}