package com.example.tbcexercises.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState

    init {
        fetchUser(1)
    }

    // this always be 1 but i want add paging
    private fun fetchUser(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _homeState.update { it.copy(loading = true) }
            try {
                val response = HomeService.fetchUsers(
                    page
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        _homeState.update {
                            it.copy(
                                loading = false,
                                success = response.body(),
                                error = null
                            )
                        }
                    } ?: unexpectedError()
                } else {
                    _homeState.update {
                        it.copy(
                            loading = false,
                            success = null,
                            error = "Can Not Fetch Users"
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _homeState.update {
                    it.copy(
                        loading = false,
                        success = null,
                        error = "Server Error"
                    )
                }
            }
        }
    }

    private fun unexpectedError() {
        _homeState.update { it.copy(loading = false, success = null, error = "Unexpected Error") }
    }

}