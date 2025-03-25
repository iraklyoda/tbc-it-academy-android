package com.iraklyoda.imageapp.presentation.screen.preview.image_picker

import android.net.Uri

data class ImagePickerState(
    val loading: Boolean = false,
    val downloadUrl: Uri? = null,
    val errorMessage: String? = null
)