package com.example.tbcexercises.presentation.screen.bottom_sheet_from_account

import com.example.tbcexercises.presentation.model.Card

data class FromAccountUiState(val isLoading: Boolean = false, val cards: List<Card> = emptyList())