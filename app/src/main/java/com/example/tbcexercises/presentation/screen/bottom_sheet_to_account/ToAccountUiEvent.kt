package com.example.tbcexercises.presentation.screen.bottom_sheet_to_account

import com.example.tbcexercises.presentation.model.Card
import com.example.tbcexercises.presentation.util.CardFindType

sealed interface ToAccountUiEvent {
    data class CardFindingByTypeChanged(val type: CardFindType) : ToAccountUiEvent
    data class GetCards(val text: String) : ToAccountUiEvent
    data class UpdateText(val newText: String) : ToAccountUiEvent
    data class OnAccountClick(val account: Card) : ToAccountUiEvent

}