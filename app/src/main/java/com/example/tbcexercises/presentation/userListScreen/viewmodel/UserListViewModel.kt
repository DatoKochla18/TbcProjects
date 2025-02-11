package com.example.tbcexercises.presentation.userListScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.tbcexercises.data.mappers.toUser
import com.example.tbcexercises.data.repository.UserRepository
import kotlinx.coroutines.flow.map

class UserListViewModel(userRepository: UserRepository) : ViewModel() {
    val usersFlow = userRepository.getUsersPager().map { pagingData ->
        pagingData.map { userEntity -> userEntity.toUser() }
    }.cachedIn(viewModelScope)

}