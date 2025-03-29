package com.example.tbcexercises.presentation.screen.bottom_sheet_from_account

sealed interface FromAccountSideEffect {
    data class ShowToast(val message: Int) : FromAccountSideEffect
}