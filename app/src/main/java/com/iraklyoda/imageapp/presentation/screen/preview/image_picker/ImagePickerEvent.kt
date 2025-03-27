package com.iraklyoda.imageapp.presentation.screen.preview.image_picker

import android.graphics.Bitmap
import android.net.Uri

sealed class ImagePickerEvent {
    data class UploadImage(val bitmap: Bitmap) : ImagePickerEvent()
    data class GenerateImageUri(val imageUri: Uri): ImagePickerEvent()
    data object UploadImageClicked : ImagePickerEvent()
    data object PickImageClicked : ImagePickerEvent()
    data object TakeAPictureClicked : ImagePickerEvent()
    data class ImageSelected(val imageUri: Uri): ImagePickerEvent()
    data object ImageTaken: ImagePickerEvent()
}