package com.example.tbcexercises.presentation.screen.home_screen

import com.example.tbcexercises.presentation.model.Card

sealed interface HomeUiEvent {
    data class UpdateFromAccount(val account: Card) : HomeUiEvent
    data class UpdateToAccount(val account: Card) : HomeUiEvent
    data class ChangeMoneyValueRight(val money: Double?) : HomeUiEvent
    data object GetCourse : HomeUiEvent
}