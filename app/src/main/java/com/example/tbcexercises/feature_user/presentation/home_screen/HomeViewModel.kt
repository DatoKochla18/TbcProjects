package com.example.tbcexercises.feature_user.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tbcexercises.feature_user.domain.use_case.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    val usersFlow = getUsersUseCase().cachedIn(viewModelScope)
}