package com.example.tricholog.utils

import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.delay

suspend fun View.fadeIn(duration: Long) {
    this.animate()
        .alpha(1f)
        .setDuration(duration)
        .start()
    delay(duration)
}

fun EditText.getString(): String {
    return this.text.toString().trim()
}

fun TextInputLayout.clearError() {
    this.error = null
    this.isErrorEnabled = false
}