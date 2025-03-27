package com.iraklyoda.imageapp.presentation.screen.preview

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraklyoda.imageapp.data.common.ImageHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val imageHelper: ImageHelper
): ViewModel() {

    private val _state: MutableStateFlow<PreviewState> = MutableStateFlow(PreviewState())
    val state: StateFlow<PreviewState> get() = _state

    private val _uiEvents: Channel<PreviewUiEvent> = Channel<PreviewUiEvent>()
    val uiEvents: Flow<PreviewUiEvent> = _uiEvents.receiveAsFlow()

    fun onEvent(event: PreviewEvent) {
        when (event) {
            is PreviewEvent.SetImage -> setImage(bitmap = event.bitmap)
            is PreviewEvent.ImageSelected -> setImage(imageUri = event.imageUri)
            is PreviewEvent.AddImageClicked -> handleImageClicked()
        }
    }

    private fun setImage(bitmap: Bitmap?) {
        viewModelScope.launch {
            _state.update { it.copy(imageBitmap = bitmap) }
        }
    }

    private fun setImage(imageUri: Uri) {
        viewModelScope.launch {
            Log.d("IMAGE_TAKEN", "IN PREVIEW VIEWMODEL setImage $imageUri")
            val bitmap = imageHelper.compressUri(imageUri = imageUri)
            Log.d("IMAGE_TAKEN", "IN PREVIEW VIEWMODEL BITMAP $bitmap")
            setImage(bitmap)
        }
    }

    private fun handleImageClicked() {
        viewModelScope.launch {
            _uiEvents.send(PreviewUiEvent.OpenImagePickerBottomSheet)
        }
    }
}
