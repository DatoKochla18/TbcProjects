package com.example.tbcexercises.feature_user.presentation.home_screen

import androidx.paging.PagingData
import com.example.tbcexercises.feature_user.presentation.model.User

data class HomeUiState(
    val isLoading: Boolean = false,
    val users: PagingData<User>? = null,
    val errorMessage: String? = null,
)