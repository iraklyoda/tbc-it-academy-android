package com.example.storeapp

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.databinding.ItemProductBinding
import com.example.storeapp.model.Product

class ProductAdapter(
    private var products: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Int, title: String, price: String, wishlistSrc: Int, product: Product) {
            binding.tvItemTitle.text = title
            binding.ivItemPicture.setImageResource(image)
            binding.tvItemPrice.text = price
            binding.btnWishlist.setImageResource(wishlistSrc)

            binding.btnWishlist.setOnClickListener {
                product.isWishlisted = !product.isWishlisted
                notifyItemChanged(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val price: String = "$${products[position].price}"
        val wishlistSrc: Int = if (products[position].isWishlisted) R.drawable.ic_heart else R.drawable.ic_heart_gray

        holder.bind(
            image = products[position].image,
            title = products[position].title,
            price = price,
            wishlistSrc = wishlistSrc,
            product = products[position]
        )
    }

}