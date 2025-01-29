package com.example.baseproject.utils

import android.util.Patterns
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import com.example.baseproject.R

fun EditText.getString(): String {
    return this.text.toString().trim()
}

fun EditText.checkEmpty(): Boolean {
    return getString().isEmpty()
}

fun EditText.isEmail(): Boolean {
    val emailString = getString()
    return Patterns.EMAIL_ADDRESS.matcher(emailString).matches()
}

fun View.shake() {
    val shake = AnimationUtils.loadAnimation(this.context, R.anim.shake)
    this.startAnimation(shake)
}