package com.example.storeapp.model

data class Product(
    val id: Int = 0,
    val image: Int,
    val title: String,
    val price: Int,
    val categoryType: Category,
    var isWishlisted: Boolean = false
)