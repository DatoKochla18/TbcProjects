package com.example.tbcexercises.feature_login.presentation.login_screen

import com.example.tbcexercises.core.domain.util.error.NetworkError

sealed interface LoginUiEvent {
    object SuccessFullLogin : LoginUiEvent
    data class ShowToast(val message: NetworkError) : LoginUiEvent
}