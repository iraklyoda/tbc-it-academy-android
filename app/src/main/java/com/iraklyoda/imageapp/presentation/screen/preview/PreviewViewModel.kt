package com.iraklyoda.imageapp.presentation.screen.preview

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(): ViewModel() {

    private val _state: MutableStateFlow<PreviewState> = MutableStateFlow(PreviewState())
    val state: StateFlow<PreviewState> get() = _state

    fun onEvent(event: PreviewEvent) {
        when (event) {
            is PreviewEvent.SetImage -> setImage(event.bitmap)
        }
    }

    private fun setImage(bitmap: Bitmap?) {
        _state.update { it.copy(imageBitmap = bitmap) }
    }
}