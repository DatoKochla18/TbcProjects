package com.example.tbcexercises.presentation.screen.home_screen

import com.example.tbcexercises.presentation.model.Card

data class HomeUiState(
    val toAccount: Card? = null,
    val fromAccount: Card? = null,
    val rate: Double = 1.0,
    val moneyRight: Double = 0.00,
    val isLoading: Boolean = false,
) {
    val showSecondMoneyConverter: Boolean
        get() = toAccount != null && fromAccount != null && toAccount.valueType != fromAccount.valueType
}