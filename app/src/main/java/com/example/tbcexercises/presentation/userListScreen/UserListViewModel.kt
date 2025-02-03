package com.example.tbcexercises.presentation.userListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.data.model.remote.UserListResponse
import com.example.tbcexercises.data.model.remote.convertUserResponseToUserEntity
import com.example.tbcexercises.data.repository.UserRepository
import com.example.tbcexercises.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserListViewModel(private val userRepository: UserRepository) : ViewModel() {
    val users = userRepository.users

    private val _userRemote = MutableStateFlow<Resource<UserListResponse>>(Resource.Loading)
    val userRemote: StateFlow<Resource<UserListResponse>> = _userRemote

    fun getUserRemote(internetConnected: Boolean) {
        if (!internetConnected) {
            _userRemote.value = Resource.Error("You are offline")
        }

        if (internetConnected) {
            _userRemote.value = Resource.Error("You are online")

            viewModelScope.launch(Dispatchers.IO) {
                userRepository.getUsersRemote().collect {
                    _userRemote.value = it

                    if (it is Resource.Success) {
                        userRepository.addUsersToDatabase(
                            it.data.users.map { userResponse -> userResponse.convertUserResponseToUserEntity() }
                        )
                    }
                }
            }
        }
    }
}