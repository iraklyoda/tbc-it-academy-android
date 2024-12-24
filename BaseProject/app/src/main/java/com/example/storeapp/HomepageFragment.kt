package com.example.storeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storeapp.databinding.FragmentHomepageBinding
import com.example.storeapp.model.Category
import com.example.storeapp.model.Product
import com.example.storeapp.model.ProductData

class HomepageFragment : Fragment() {
    private var _binding: FragmentHomepageBinding? = null
    private val binding: FragmentHomepageBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)

        val categories = Category.entries

        fun getProducts(selectedCategory: Category = Category.ALL) {
            val productList: List<Product> = ProductData.getProducts(selectedCategory)
            val productAdapter = ProductAdapter(productList)
            binding.rvProducts.adapter = productAdapter
            binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        }
        getProducts()

        val categoryAdapter = CategoryAdapter(categories)
        { selectedCategory: Category ->
            getProducts(selectedCategory)
        }

        binding.rvCategories.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvCategories.adapter = categoryAdapter



        return binding.root
    }


}