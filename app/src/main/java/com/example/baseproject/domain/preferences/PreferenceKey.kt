package com.example.baseproject.domain.preferences

sealed class PreferenceKey<T>(
    val keyName: String,
    val defaultValue: T,
    val type: PreferenceType
) {
    class StringKey(keyName: String, defaultValue: String = "") :
        PreferenceKey<String>(keyName, defaultValue, PreferenceType.STRING)

    class IntKey(keyName: String, defaultValue: Int = 0) :
        PreferenceKey<Int>(keyName, defaultValue, PreferenceType.INT)

    class BooleanKey(keyName: String, defaultValue: Boolean = false) :
        PreferenceKey<Boolean>(keyName, defaultValue, PreferenceType.BOOLEAN)
}

enum class PreferenceType {
    STRING, INT, BOOLEAN
}