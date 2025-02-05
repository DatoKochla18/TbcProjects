package com.example.tbcexercises.presentation.entryScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.R
import com.example.tbcexercises.util.getDialogs
import com.example.tbcexercises.util.getPasswords
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EntryScreenViewModel : ViewModel() {
    val dialogs = getDialogs()

    private val _uiState = MutableStateFlow(getPasswords())
    val uiState = _uiState.asStateFlow()

    private val _answerFlow = MutableSharedFlow<Int>()
    val answerFlow = _answerFlow.asSharedFlow()

    fun appendPassword(number: Int) {
        val password = _uiState.value.firstOrNull { password -> password.enteredNumber == null }
        if (password != null) {
            _uiState.update {
                it.map { oldPassword ->
                    if (oldPassword.id == password.id) password.copy(
                        enteredNumber = number
                    ) else oldPassword
                }
            }
        }

        if (_uiState.value.all { it.enteredNumber != null }) {
            checkPassword()


        }
    }

    private fun clearPassword() {
        _uiState.update {
            it.map { password -> password.copy(enteredNumber = null) }
        }
    }

    private fun checkPassword() {
        val enteredCode = _uiState.value.map { it.enteredNumber }.joinToString("")
        viewModelScope.launch {
            _answerFlow.emit(if (enteredCode == "0934") R.string.logged_in else R.string.wrong)
        }
        clearPassword()
    }

    fun deletePassword() {
        val password = _uiState.value.lastOrNull { password -> password.enteredNumber != null }
        if (password != null) {
            _uiState.update {
                it.map { oldPassword ->
                    if (oldPassword.id == password.id) password.copy(
                        enteredNumber = null
                    ) else oldPassword
                }
            }
        }
    }
}