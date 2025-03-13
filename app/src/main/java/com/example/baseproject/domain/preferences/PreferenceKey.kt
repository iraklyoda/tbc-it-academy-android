package com.example.baseproject.domain.preferences

sealed class PreferenceKey<T>(
    val keyName: String,
) {
    class StringKey(keyName: String) :
        PreferenceKey<String>(keyName)

    class IntKey(keyName: String) :
        PreferenceKey<Int>(keyName)

    class BooleanKey(keyName: String) :
        PreferenceKey<Boolean>(keyName)
}
