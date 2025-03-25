package com.iraklyoda.imageapp.presentation.screen.preview.image_picker

import android.graphics.Bitmap

sealed class ImagePickerEvent {
    data class UploadImage(val bitmap: Bitmap) : ImagePickerEvent()

}