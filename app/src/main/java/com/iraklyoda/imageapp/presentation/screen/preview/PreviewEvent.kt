package com.iraklyoda.imageapp.presentation.screen.preview

import android.graphics.Bitmap
import android.net.Uri

sealed class PreviewEvent {
    data class SetImage(val bitmap: Bitmap) : PreviewEvent()
    data class ImageSelected(val imageUri: Uri) : PreviewEvent()
    data object AddImageClicked: PreviewEvent()
}