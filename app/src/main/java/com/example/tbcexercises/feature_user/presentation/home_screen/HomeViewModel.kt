package com.example.tbcexercises.feature_user.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.tbcexercises.feature_user.domain.use_case.GetUsersUseCase
import com.example.tbcexercises.feature_user.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUsersUseCase: GetUsersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _events = Channel<HomeEvent>()
    val events = _events.receiveAsFlow()

    private val usersFlow = getUsersUseCase().cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            delay(1000L)
            usersFlow.collectLatest { pagingData ->
                _uiState.update { homeUiState ->
                    homeUiState.copy(
                        users = pagingData.map { it.toPresentation() },
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
        }
    }

    fun onLoadStateChanged(loadState: CombinedLoadStates) {
        val refreshState = loadState.mediator?.refresh ?: loadState.source.refresh

        when (refreshState) {
            is LoadState.Loading -> {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            }

            is LoadState.Error -> {
                val errorMsg = refreshState.error.localizedMessage
                _uiState.update { it.copy(isLoading = false, errorMessage = errorMsg) }
                viewModelScope.launch {
                    errorMsg?.let {
                        _events.send(HomeEvent.ShowError(it))
                    }
                }
            }

            is LoadState.NotLoading -> {
                _uiState.update { it.copy(isLoading = false, errorMessage = null) }
            }
        }
    }
}
