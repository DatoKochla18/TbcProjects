package com.example.tbcexercises.feature_login.presentation.login_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.core.presentation.util.Constants.EMAIL_KEY
import com.example.tbcexercises.core.presentation.util.Constants.REMEMBER_ME_KEY
import com.example.tbcexercises.core.utils.Resource
import com.example.tbcexercises.feature_login.domain.use_case.LoginUseCaseWrapper
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
    private val loginUseCaseWrapper: LoginUseCaseWrapper,
) :
    ViewModel() {
    private val _uiState =
        MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun setSession(rememberMe: Boolean, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCaseWrapper.saveValueToLocalStorageUseCase(REMEMBER_ME_KEY, rememberMe)
            loginUseCaseWrapper.saveValueToLocalStorageUseCase(EMAIL_KEY, email)
        }
    }

    private val _uiEventChannel = Channel<LoginUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()


    fun onEvent(event: LoginValidationEvent) {
        when (event) {
            is LoginValidationEvent.LoginValidation -> login(event.email, event.password)
            is LoginValidationEvent.ValidateEmail -> validateEmail(event.email)
            is LoginValidationEvent.ValidatePassword -> validatePassword(event.password)
        }
    }

    private fun login(email: String, password: String) {
        if (!validateForm(email, password)) return

        viewModelScope.launch {
            loginUseCaseWrapper.loginUseCase(email, password).collect { result ->
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
        val emailResult = loginUseCaseWrapper.validateEmailUseCase(email)
        val passwordResult = loginUseCaseWrapper.validatePasswordUseCase(password)

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
        val result = loginUseCaseWrapper.validateEmailUseCase(email)
        Log.d("result", result.toString())
        _uiState.update {
            it.copy(
                emailError = result.errorMessage,
                isValidForm = result.successful && (_uiState.value.passwordError == null)

            )
        }
    }

    private fun validatePassword(password: String) {
        val result = loginUseCaseWrapper.validatePasswordUseCase(password)
        _uiState.update {
            it.copy(
                passwordError = result.errorMessage,
                isValidForm = result.successful && (_uiState.value.emailError == null)
            )
        }
    }
}