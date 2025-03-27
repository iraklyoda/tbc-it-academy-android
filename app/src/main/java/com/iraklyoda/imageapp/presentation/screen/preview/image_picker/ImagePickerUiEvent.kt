package com.iraklyoda.imageapp.presentation.screen.preview.image_picker

import android.net.Uri

sealed class ImagePickerUiEvent {
    data class ShowError(val error: String?): ImagePickerUiEvent()
    data class DismissDialog(val imageUri: Uri?): ImagePickerUiEvent()
    data object LaunchMediaPicker: ImagePickerUiEvent()
    data object LaunchCamera: ImagePickerUiEvent()
    data object LaunchMediaUploader: ImagePickerUiEvent()
}