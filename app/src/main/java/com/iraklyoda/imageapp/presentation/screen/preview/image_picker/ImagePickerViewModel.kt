package com.iraklyoda.imageapp.presentation.screen.preview.image_picker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraklyoda.imageapp.domain.common.repository.ImageRepository
import com.iraklyoda.imageapp.domain.common.use_case.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagePickerViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<ImagePickerState> = MutableStateFlow(ImagePickerState())
    val state: StateFlow<ImagePickerState> get() = _state

    private val _uiEvents = Channel<ImagePickerUiEvent>()
    val uiEvents: Flow<ImagePickerUiEvent> = _uiEvents.receiveAsFlow()


    fun onEvent(event: ImagePickerEvent) {
        when (event) {
            is ImagePickerEvent.UploadImage -> {

            }
            is ImagePickerEvent.PickImageClicked -> handlePickImageClicked()
            is ImagePickerEvent.UploadImageClicked -> handleUploadImageClicked()
            is ImagePickerEvent.TakeAPictureClicked -> handleTakeAPictureClicked()
        }
    }


    private fun handlePickImageClicked() {
        viewModelScope.launch {
            _uiEvents.send(ImagePickerUiEvent.LaunchMediaUploader)
        }    }

    private fun handleTakeAPictureClicked() {
        viewModelScope.launch {
            _uiEvents.send(ImagePickerUiEvent.LaunchCamera)
        }
    }

    private fun handleUploadImageClicked() {
        viewModelScope.launch {
            _uiEvents.send(ImagePickerUiEvent.LaunchMediaUploader)
        }
    }
}