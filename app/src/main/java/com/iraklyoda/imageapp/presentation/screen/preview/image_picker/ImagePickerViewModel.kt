package com.iraklyoda.imageapp.presentation.screen.preview.image_picker

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraklyoda.imageapp.data.repository.ImageRepository
import com.iraklyoda.imageapp.domain.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagePickerViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _state: MutableStateFlow<ImagePickerState> = MutableStateFlow(ImagePickerState())
    val state: StateFlow<ImagePickerState> get() = _state

    private val _uiEvents = Channel<ImagePickerUiEvent>()
    val uiEvents: Flow<ImagePickerUiEvent> = _uiEvents.receiveAsFlow()

    fun onEvent(event: ImagePickerEvent) {
        when (event) {
            is ImagePickerEvent.UploadImage -> uploadImageToStorage(bitmap = event.bitmap)
        }
    }

    private fun uploadImageToStorage(bitmap: Bitmap) {
        viewModelScope.launch {
            imageRepository.uploadBitmap(bitmap).collectLatest { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        _state.update { it.copy(loading = resource.loading) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(downloadUrl = resource.data)
                        }
                        _uiEvents.send(ImagePickerUiEvent.DismissDialog(imageUri = state.value.downloadUrl))
                    }

                    is Resource.Error -> {
                        _state.update { it.copy(errorMessage = resource.errorMessage) }
                        _uiEvents.send(ImagePickerUiEvent.ShowError(error = state.value.errorMessage))
                    }
                }
            }
        }
    }

}