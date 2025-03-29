package com.example.tbcexercises.presentation.screen.bottom_sheet_from_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.use_case.GetCardsUseCase
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.presentation.extension.asStringResource
import com.example.tbcexercises.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FromAccountViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FromAccountUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<FromAccountSideEffect>()
    val sideEffect = _sideEffects.asSharedFlow()

    fun onEvent(event: FromAccountEvent) {
        when (event) {
            FromAccountEvent.GetCards -> getCards()
        }
    }

    private fun getCards() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = getCardsUseCase()) {
                is Resource.Error -> _sideEffects.emit(FromAccountSideEffect.ShowToast(result.error.asStringResource()))
                is Resource.Success -> _uiState.update { fromAccountUiState ->
                    fromAccountUiState.copy(
                        isLoading = false,
                        cards = result.data.map { it.toPresentation() })
                }
            }
        }
    }
}