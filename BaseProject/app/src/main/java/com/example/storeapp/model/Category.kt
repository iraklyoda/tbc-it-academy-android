package com.example.storeapp.model

enum class Category(val displayName: String, val icon: String?, var isActive: Boolean = false) {
    ALL(displayName = "All", null, isActive = true),
    PARTY(displayName = "Party", icon = "🎉"),
    CAMPING(displayName = "Camping", icon = "🏕"),
    CATEGORY1(displayName = "Category 1", icon = "🔥"),
    CATEGORY2(displayName = "Category 2", icon = "🕺"),
    CATEGORY3(displayName = "Category 3", icon = "👽");


    fun setActive() {
        entries.forEach { it.isActive = false }
        this.isActive = true
    }
}