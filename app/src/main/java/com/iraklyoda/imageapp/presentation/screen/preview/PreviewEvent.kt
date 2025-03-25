package com.iraklyoda.imageapp.presentation.screen.preview

import android.graphics.Bitmap

sealed class PreviewEvent {
    data class SetImage(val bitmap: Bitmap?): PreviewEvent()
}