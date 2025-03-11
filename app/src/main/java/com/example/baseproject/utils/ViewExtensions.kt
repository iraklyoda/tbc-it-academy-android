package com.example.baseproject.utils

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
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
    } else {
        this.visibility = View.GONE
    }
}

fun EditText.onTextChanged(action: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            action(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}