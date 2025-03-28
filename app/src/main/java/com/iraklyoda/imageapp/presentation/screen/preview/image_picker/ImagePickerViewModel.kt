package com.iraklyoda.imageapp.presentation.screen.preview.image_picker

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.iraklyoda.imageapp.domain.common.use_case.UploadImageUseCase
import com.iraklyoda.imageapp.presentation.worker.UploadWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
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
            // Take Picture
            is ImagePickerEvent.TakeAPictureClicked -> handleTakeAPictureClicked()
            is ImagePickerEvent.GenerateImageUri -> handleGenerateUri(imageUri = event.imageUri)
            is ImagePickerEvent.ImageTaken -> state.value.imageUri?.let {
                handleImageSelected(it)
            }

            // Select Picture
            is ImagePickerEvent.PickImageClicked -> handlePickImageClicked()
            is ImagePickerEvent.ImageSelected -> handleImageSelected(event.imageUri)

            // Upload Picture
            is ImagePickerEvent.UploadImage -> {}
            is ImagePickerEvent.UploadImageClicked -> handleUploadImageClicked()
        }
    }


    private fun handleImageSelected(imageUri: Uri) {
        viewModelScope.launch {
            _uiEvents.send(ImagePickerUiEvent.ImageSelected(imageUri = imageUri))
        }
    }


    private fun handleGenerateUri(imageUri: Uri) {
        viewModelScope.launch {
            updateImageUriState(imageUri = imageUri)
            _uiEvents.send(ImagePickerUiEvent.LaunchCamera(imageUri = imageUri))
        }
    }

    private fun updateImageUriState(imageUri: Uri?) {
        _state.update { it.copy(imageUri = imageUri) }

    }

    private fun handlePickImageClicked() {
        viewModelScope.launch {
            _uiEvents.send(ImagePickerUiEvent.LaunchMediaPicker)
        }
    }

    private fun handleTakeAPictureClicked() {
        viewModelScope.launch {
            _uiEvents.send(ImagePickerUiEvent.GenerateUri)
        }
    }

    private fun handleUploadImageClicked() {
        viewModelScope.launch {
            _uiEvents.send(ImagePickerUiEvent.LaunchMediaUploader)
        }
    }
}