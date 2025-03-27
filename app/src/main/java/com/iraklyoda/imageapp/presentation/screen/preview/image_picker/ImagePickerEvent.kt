package com.iraklyoda.imageapp.presentation.screen.preview.image_picker

import android.graphics.Bitmap

sealed class ImagePickerEvent {
    data class UploadImage(val bitmap: Bitmap) : ImagePickerEvent()
    data object UploadImageClicked : ImagePickerEvent()
    data object PickImageClicked : ImagePickerEvent()
    data object TakeAPictureClicked : ImagePickerEvent()
}