package com.iraklyoda.imageapp.presentation.screen.preview.image_picker

import android.net.Uri

sealed class ImagePickerUiEvent {
    data class ShowError(val error: String?) : ImagePickerUiEvent()
    data class ImageSelected(val imageUri: Uri) : ImagePickerUiEvent()
    data object LaunchMediaPicker : ImagePickerUiEvent()
    data object GenerateUri : ImagePickerUiEvent()
    data class LaunchCamera(val imageUri: Uri) : ImagePickerUiEvent()
    data object LaunchMediaUploader : ImagePickerUiEvent()
}