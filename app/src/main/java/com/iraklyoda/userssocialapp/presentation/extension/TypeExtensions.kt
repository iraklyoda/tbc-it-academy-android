package com.iraklyoda.userssocialapp.presentation.extension

import android.util.Patterns

fun String.isEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
