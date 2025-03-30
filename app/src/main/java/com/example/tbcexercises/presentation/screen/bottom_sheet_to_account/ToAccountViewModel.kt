package com.example.tbcexercises.presentation.screen.bottom_sheet_to_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.R
import com.example.tbcexercises.domain.use_case.GetCardCheckUseCase
import com.example.tbcexercises.domain.use_case.GetCardsUseCase
import com.example.tbcexercises.domain.use_case.validation.ValidateAccountNumberUseCase
import com.example.tbcexercises.domain.use_case.validation.ValidatePersonalNumberUseCase
import com.example.tbcexercises.domain.use_case.validation.ValidatePhoneNumberUseCase
import com.example.tbcexercises.domain.util.CardCheckStatus
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.presentation.extension.asStringResource
import com.example.tbcexercises.presentation.extension.toDomain
import com.example.tbcexercises.presentation.mapper.toPresentation
import com.example.tbcexercises.presentation.model.Card
import com.example.tbcexercises.presentation.util.CardFindType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToAccountViewModel @Inject constructor(
    private val getCardUseCase: GetCardsUseCase,
    private val validatePersonalNumberUseCase: ValidatePersonalNumberUseCase,
    private val validateAccountNumberUseCase: ValidateAccountNumberUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val getCardCheckUseCase: GetCardCheckUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ToAccountUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<ToAccountSideEffect>()
    val sideEffect = _sideEffects.asSharedFlow()

    fun onEvent(event: ToAccountUiEvent) {
        when (event) {
            is ToAccountUiEvent.CardFindingByTypeChanged -> _uiState.update { currentState ->
                val newType = when (event.type) {
                    is CardFindType.AccountNumber -> CardFindType.AccountNumber(currentState.cardFindingTypeText())
                    is CardFindType.PhoneNumber -> CardFindType.PhoneNumber(currentState.cardFindingTypeText())
                    is CardFindType.PersonalNumber -> CardFindType.PersonalNumber(currentState.cardFindingTypeText())
                }
                currentState.copy(
                    cardFindingType = newType,
                    validationError = getValidationUseCaseResultByCardFindingType(newType),
                    cards = emptyList()
                )
            }

            is ToAccountUiEvent.UpdateText -> _uiState.update { currentState ->
                val updatedType = when (currentState.cardFindingType) {
                    is CardFindType.AccountNumber -> CardFindType.AccountNumber(event.newText)
                    is CardFindType.PhoneNumber -> CardFindType.PhoneNumber(event.newText)
                    is CardFindType.PersonalNumber -> CardFindType.PersonalNumber(event.newText)
                }
                currentState.copy(
                    cardFindingType = updatedType,
                    validationError = getValidationUseCaseResultByCardFindingType(updatedType),
                    cards = emptyList()
                )
            }

            is ToAccountUiEvent.GetCards -> getCards()
            is ToAccountUiEvent.OnAccountClick -> getCardCheck(event.account)
        }
    }

    private fun getCards() {
        if (_uiState.value.validationError != null) return

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getCardUseCase(_uiState.value.cardFindingType.toDomain())) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _sideEffects.emit(ToAccountSideEffect.ShowError(result.error.asStringResource()))
                }

                is Resource.Success -> _uiState.update {
                    it.copy(
                        cards = result.data.map { it.toPresentation() },
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getCardCheck(account: Card) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getCardCheckUseCase()) {
                is Resource.Error -> _sideEffects.emit(ToAccountSideEffect.ShowError(result.error.asStringResource()))
                is Resource.Success -> {
                    if (result.data.status == CardCheckStatus.SUCCESS) {
                        _sideEffects.emit(ToAccountSideEffect.SuccessfulCard(account))
                    } else {
                        _sideEffects.emit(ToAccountSideEffect.ShowError(R.string.opps_bad_card))
                    }

                }
            }
        }
    }


    private fun getValidationUseCaseResultByCardFindingType(
        cardFindType: CardFindType,
    ): Int? {

        val result = when (cardFindType) {
            is CardFindType.AccountNumber -> validateAccountNumberUseCase(cardFindType.text)
            is CardFindType.PersonalNumber -> validatePersonalNumberUseCase(cardFindType.text)
            is CardFindType.PhoneNumber -> validatePhoneNumberUseCase(cardFindType.text)
        }
        return when (result) {
            is Resource.Error -> result.error.asStringResource()
            is Resource.Success -> {
                _uiState.update { it.copy(isButtonEnabled = true) }
                null
            }
        }
    }


}