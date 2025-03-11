package com.example.baseproject.domain.preferences

sealed class PreferenceKey<T>(
    val keyName: String,
    val defaultValue: T,
) {
    class StringKey(keyName: String, defaultValue: String = "") :
        PreferenceKey<String>(keyName, defaultValue)

    class IntKey(keyName: String, defaultValue: Int = 0) :
        PreferenceKey<Int>(keyName, defaultValue)

    class BooleanKey(keyName: String, defaultValue: Boolean = false) :
        PreferenceKey<Boolean>(keyName, defaultValue)
}
