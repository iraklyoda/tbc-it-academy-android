package com.iraklyoda.imageapp.presentation.screen.preview

import android.graphics.Bitmap

sealed class PreviewUiEvent {
    data object OpenImagePickerBottomSheet: PreviewUiEvent()
}