package com.example.tbcexercises.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import androidx.paging.cachedIn
import androidx.paging.map
import com.example.tbcexercises.data.mappers.toUser
import com.example.tbcexercises.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(userRepository: UserRepository) : ViewModel() {
    val usersFlow = userRepository.getUsersPager().map { pagingData ->
        pagingData.map { userEntity -> userEntity.toUser() }
    }.cachedIn(viewModelScope)
}