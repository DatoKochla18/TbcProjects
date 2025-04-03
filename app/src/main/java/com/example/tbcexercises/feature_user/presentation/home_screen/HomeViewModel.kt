package com.example.tbcexercises.feature_user.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.tbcexercises.feature_user.domain.use_case.GetUsersUseCase
import com.example.tbcexercises.feature_user.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUsersUseCase: GetUsersUseCase,
) : ViewModel() {
    val users = getUsersUseCase().cachedIn(viewModelScope).map { it.map { it.toPresentation() } }
}
