package com.example.baseproject.utils

import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton

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