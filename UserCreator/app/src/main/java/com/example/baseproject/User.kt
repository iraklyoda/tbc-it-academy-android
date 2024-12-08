package com.example.baseproject

class User(val fullName: String, val email: String) {
    companion object {
        var usersCount: Int = 0
        val usersMap: MutableMap<String, String> = mutableMapOf<String, String>()
    }

    init {
        usersCount++
        usersMap[email.lowercase()] = fullName.lowercase()
    }
}