package com.example.baseproject.utils

import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar

// Takes EditText View as a parameter and updates InputType based on condition
// Best suited for password types

fun ImageButton.makeVisibilityToggle(editText: EditText) {
    this.setOnClickListener {
        if (editText.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editText.setSelection(editText.text.toString().length)
        } else {
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setSelection(editText.text.toString().length)
        }
    }
}

fun EditText.getString(): String {
    return this.text.toString().trim()
}

fun ProgressBar.setLoaderState(loading: Boolean, actionBtn: Button? = null) {
    if (loading) {
        this.visibility = View.VISIBLE
        actionBtn?.isEnabled = false
    } else {
        this.visibility = View.GONE
        actionBtn?.isEnabled = true
    }
}