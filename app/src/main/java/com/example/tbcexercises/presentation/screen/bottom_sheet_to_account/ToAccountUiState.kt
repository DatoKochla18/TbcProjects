package com.example.tbcexercises.presentation.screen.bottom_sheet_to_account

import com.example.tbcexercises.presentation.model.Card
import com.example.tbcexercises.presentation.util.CardFindType

data class ToAccountUiState(
    val isLoading: Boolean = false,
    val validationError: Int? = null,
    val cardFindingType: CardFindType = CardFindType.AccountNumber(""),
    val cards: List<Card> = emptyList(),
    val isButtonEnabled: Boolean = false,
)

fun ToAccountUiState.cardFindingTypeText(): String = when (cardFindingType) {
    is CardFindType.AccountNumber -> cardFindingType.text
    is CardFindType.PhoneNumber -> cardFindingType.text
    is CardFindType.PersonalNumber -> cardFindingType.text
}