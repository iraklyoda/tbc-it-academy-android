package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.to

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraklyoda.transferapp.domain.common.Resource
import com.iraklyoda.transferapp.domain.common.ValidationResult
import com.iraklyoda.transferapp.domain.use_case.account.GetReceiverAccountUseCase
import com.iraklyoda.transferapp.domain.use_case.account.ValidateAccountIdUseCase
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.mapper.toUi
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
class ToAccountViewModel @Inject constructor(
    private val validateAccountIdUseCase: ValidateAccountIdUseCase,
    private val getReceiverAccountUseCase: GetReceiverAccountUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<ToAccountState> = MutableStateFlow(ToAccountState())
    val state: StateFlow<ToAccountState> get() = _state

    private val _uiEvents: Channel<ToAccountUiEvent> = Channel()
    val uiEvents: Flow<ToAccountUiEvent> get() = _uiEvents.receiveAsFlow()

    fun onEvent(event: ToAccountEvent) {
        when (event) {
            is ToAccountEvent.CheckIdClicked -> validateIdentifier(identifier = event.identifier)
        }
    }

    private fun validateIdentifier(identifier: String) {
        when(val result = validateAccountIdUseCase(identifier = identifier)) {
            is ValidationResult.Success -> handleIdentifierInputSuccess(identifier = identifier)
            is ValidationResult.Error -> setIdentifierInputError(errorMessage = result.errorMessage)
        }
    }

    private fun fetchAccount(identifier: String) {
        viewModelScope.launch {
            getReceiverAccountUseCase(identifier = identifier).collectLatest { resource ->

                Log.d("RECEIVE_ACCOUNT", resource.toString())

                when(resource) {
                    is Resource.Loader -> _state.update {
                        it.copy(isLoading = resource.loading)
                    }

                    is Resource.Success -> _state.update {
                        _uiEvents.send(ToAccountUiEvent.HandleAccountReceived(account = resource.data.toUi()))
                        it.copy(account = resource.data.toUi())
                    }

                    is Resource.Error -> _state.update {
                        _uiEvents.send(ToAccountUiEvent.HandleFetchingError(errorMessage = resource.errorMessage))
                        it.copy(errorMessage = resource.errorMessage)
                    }
                }
            }
        }
    }

    private fun handleIdentifierInputSuccess(identifier: String) {
        viewModelScope.launch {
            _state.update { it.copy(sendInputErrorMessage = null) }
            fetchAccount(identifier = identifier)
        }
    }

    private fun setIdentifierInputError(errorMessage: String) {
        viewModelScope.launch {
            _state.update { it.copy(sendInputErrorMessage = errorMessage) }
        }
    }
}