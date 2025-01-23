package com.example.baseproject.utils

import android.content.Context
import android.widget.Toast

fun Context.showErrorToast(message: String, duration: Int = Toast.LENGTH_SHORT): Boolean {
    Toast.makeText(this, message, duration).show()
    return false
}