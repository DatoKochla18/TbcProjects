package com.example.tbcexercises.presentation.screen.bottom_sheet_from_account

sealed interface FromAccountEvent {
    data object GetCards: FromAccountEvent
}