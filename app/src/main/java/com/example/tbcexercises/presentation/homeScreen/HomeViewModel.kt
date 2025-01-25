package com.example.tbcexercises.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.R
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
                            error = R.string.can_not_fetch_users
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _homeState.update {
                    it.copy(
                        loading = false,
                        success = null,
                        error = R.string.server_error
                    )
                }
            }
        }
    }

    private fun unexpectedError() {
        _homeState.update {
            it.copy(
                loading = false,
                success = null,
                error = R.string.unexpected_error
            )
        }
    }

}