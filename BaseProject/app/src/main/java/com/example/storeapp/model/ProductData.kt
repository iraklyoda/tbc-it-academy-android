package com.example.storeapp.model

import com.example.storeapp.R

object ProductData {
    private fun getProducts(): List<Product> {
        return listOf(
            Product(image = R.drawable.model_01, title = "Hoodie", price = 20, categoryType = Category.CAMPING),
            Product(image = R.drawable.model_02, title = "Shirt", price = 20, categoryType = Category.CATEGORY2),
            Product(image = R.drawable.model_03, title = "Shirt", price = 20, categoryType = Category.ALL),
            Product(image = R.drawable.model_04, title = "Shirt", price = 40, categoryType = Category.PARTY, isWishlisted = true),
            Product(image = R.drawable.model_01, title = "Shirt", price = 20, categoryType = Category.CATEGORY3),
            Product(image = R.drawable.model_02, title = "Shirt", price = 20, categoryType = Category.CATEGORY3),
            Product(image = R.drawable.model_03, title = "Shirt", price = 20, categoryType =  Category.CATEGORY1),
            Product(image = R.drawable.model_04, title = "Shirt", price = 20, categoryType = Category.CATEGORY1)
        )
    }

    fun getProducts(category: Category): List<Product> {
        if(category == Category.ALL) {
            return getProducts()
        }
        return getProducts().filter { it.categoryType == category }
    }
}