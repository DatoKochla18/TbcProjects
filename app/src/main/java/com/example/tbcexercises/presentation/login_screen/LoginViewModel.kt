package com.example.tbcexercises.presentation.login_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.domain.manager.UserSessionManager
import com.example.tbcexercises.domain.use_case.LoginUseCase
import com.example.tbcexercises.domain.use_case.validation.ValidateEmailUseCase
import com.example.tbcexercises.domain.use_case.validation.ValidatePasswordUseCase
import com.example.tbcexercises.utils.Constants.EMAIL_KEY
import com.example.tbcexercises.utils.Constants.REMEMBER_ME_KEY
import com.example.tbcexercises.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase,
    private val userSessionManager: UserSessionManager
) :
    ViewModel() {
    private val _uiState =
        MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun setSession(rememberMe: Boolean, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userSessionManager.saveValue(REMEMBER_ME_KEY, rememberMe)
            userSessionManager.saveValue(EMAIL_KEY, email)
        }
    }

    private val _uiEventChannel = Channel<LoginUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login(event.email, event.password)
            is LoginEvent.ValidateEmail -> validateEmail(event.email)
            is LoginEvent.ValidatePassword -> validatePassword(event.password)
        }
    }

    private fun login(email: String, password: String) {
        if (!validateForm(email, password)) return

        viewModelScope.launch {
            loginUseCase(email, password).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _uiEventChannel.send(LoginUiEvent.SuccessFullLogin)

                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                        _uiEventChannel.send(LoginUiEvent.ShowToast(result.message))
                    }
                }
            }
        }
    }


    private fun validateForm(email: String, password: String): Boolean {
        val emailResult = validateEmailUseCase(email)
        val passwordResult = validatePasswordUseCase(password)

        val hasError = !emailResult.successful || !passwordResult.successful

        _uiState.update {
            it.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                isValidForm = !hasError
            )
        }

        return !hasError
    }

    private fun validateEmail(email: String) {
        val result = validateEmailUseCase(email)
        Log.d("result", result.toString())
        _uiState.update {
            it.copy(
                emailError = result.errorMessage,
                isValidForm = result.successful && (_uiState.value.passwordError == null)
            )
        }
    }

    private fun validatePassword(password: String) {
        val result = validatePasswordUseCase(password)
        _uiState.update {
            it.copy(
                passwordError = result.errorMessage,
                isValidForm = result.successful && (_uiState.value.emailError == null)
            )
        }
    }
}