package com.example.tbcexercises.presentation.screen.bottom_sheet_to_account

import com.example.tbcexercises.presentation.model.Card

sealed interface ToAccountSideEffect {
    data class ShowError(val message: Int) : ToAccountSideEffect
    data class SuccessfulCard(val account: Card) : ToAccountSideEffect
}