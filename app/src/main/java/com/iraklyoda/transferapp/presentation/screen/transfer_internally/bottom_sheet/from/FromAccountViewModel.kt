package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraklyoda.transferapp.domain.common.Resource
import com.iraklyoda.transferapp.domain.use_case.account.GetAccountsUseCase
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.event.FromAccountEvent
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.event.FromAccountUiEvent
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.mapper.toUi
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi
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
class FromAccountViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FromAccountState())
    val state: StateFlow<FromAccountState> get() = _state

    private val _uiEvent: Channel<FromAccountUiEvent> = Channel()
    val event: Flow<FromAccountUiEvent> get() = _uiEvent.receiveAsFlow()

    fun onEvent(event: FromAccountEvent) {
        when (event) {
            is FromAccountEvent.FetchDebitCards -> fetchDebitCards()
            is FromAccountEvent.AccountClicked -> onAccountChosen(account = event.account)
        }
    }

    private fun fetchDebitCards() {
        viewModelScope.launch {
            getAccountsUseCase().collectLatest { resource ->
                when (resource) {
                    is Resource.Loader -> _state.update {
                        Log.d("NO_INTERNET_CARDS", resource.loading.toString())
                        it.copy(isLoading = resource.loading)
                    }

                    is Resource.Success -> _state.update {
                        it.copy(accounts = resource.data.map { getDebitCard -> getDebitCard.toUi() })
                    }

                    is Resource.Error -> onFetchingError(errorMessage = resource.errorMessage)
                }
            }
        }
    }

    private fun onAccountChosen(account: AccountUi) {
        viewModelScope.launch {
            _uiEvent.send(FromAccountUiEvent.HandleChosenAccount(account = account))
        }
    }

    private fun onFetchingError(errorMessage: String) {
        viewModelScope.launch {
            _uiEvent.send(FromAccountUiEvent.HandleFetchingError(errorMessage = errorMessage))
            _state.update { it.copy(errorMessage = errorMessage) }
        }
    }

}