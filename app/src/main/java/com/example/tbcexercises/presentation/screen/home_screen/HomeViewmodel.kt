package com.example.tbcexercises.presentation.screen.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.use_case.GetCourseUseCase
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.presentation.extension.asStringResource
import com.example.tbcexercises.presentation.mapper.toPresentation
import com.example.tbcexercises.presentation.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()


    private val _sideEffects = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffects.asSharedFlow()

    init {
        onEvent(HomeUiEvent.GetCourse)
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ChangeMoneyValueRight -> changeMoneyRight(event.money)
            is HomeUiEvent.UpdateFromAccount -> updateFromAccount(event.account)
            is HomeUiEvent.UpdateToAccount -> updateToAccount(event.account)
            HomeUiEvent.GetCourse -> getCourse()
        }
    }

    private fun getCourse() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getCourseUseCase()) {
                is Resource.Error -> _sideEffects.emit(HomeSideEffect.ShowError(result.error.asStringResource()))
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            rate = result.data.toPresentation().course
                        )
                    }
                }
            }
        }
    }

    private fun updateFromAccount(account: Card) {
        _uiState.update { it.copy(fromAccount = account) }
    }

    private fun updateToAccount(account: Card) {
        _uiState.update { it.copy(toAccount = account) }
    }

    private fun changeMoneyRight(money: Double?) {
        _uiState.update { it.copy(moneyRight = (money ?: 0.0) * it.rate) }
    }
}