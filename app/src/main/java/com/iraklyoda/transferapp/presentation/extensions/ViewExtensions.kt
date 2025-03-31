package com.iraklyoda.transferapp.presentation.extensions

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar

fun View.updateVisibility(vararg views: View, visibility: Boolean) {
    views.forEach { it.isVisible = visibility }
}

fun View.updateInvisibility(vararg views: View, inVisibility: Boolean) {
    views.forEach { it.isInvisible = inVisibility }
}

fun View.showSnackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
) {
    Snackbar.make(this, message, duration).show()
}