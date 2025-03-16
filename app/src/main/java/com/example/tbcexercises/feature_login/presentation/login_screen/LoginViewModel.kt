package com.example.tbcexercises.feature_login.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.core.presentation.util.PreferenceKeys.EMAIL_KEY
import com.example.tbcexercises.core.presentation.util.PreferenceKeys.REMEMBER_ME_KEY
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

    private val _uiEventChannel = Channel<LoginSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()


    fun onEvent(event: LoginValidationEvent) {
        when (event) {
            is LoginValidationEvent.Login -> login(event.email, event.password)
            is LoginValidationEvent.ValidateEmail -> validateEmail(event.email)
            is LoginValidationEvent.ValidatePassword -> validatePassword(event.password)
        }
    }

    private fun login(email: String, password: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            loginUseCaseWrapper.loginUseCase(email, password).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                        _uiEventChannel.send(LoginSideEffect.ShowToast(result.error))
                    }

                    is Result.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _uiEventChannel.send(LoginSideEffect.SuccessFullLogin)
                    }
                }
            }
        }
    }

    private fun validateEmail(email: String) {
        when (val result = loginUseCaseWrapper.validateEmailUseCase(email)) {
            is Result.Error -> _uiState.update { it.copy(emailError = result.error) }
            is Result.Success -> _uiState.update {
                it.copy(
                    emailError = null,
                    isEmailValid = true,
                    isValidForm = _uiState.value.isPasswordValid
                )
            }
        }
    }

    private fun validatePassword(password: String) {
        when (val result = loginUseCaseWrapper.validatePasswordUseCase(password)) {
            is Result.Error -> _uiState.update { it.copy(passwordError = result.error) }
            is Result.Success -> _uiState.update {
                it.copy(
                    passwordError = null,
                    isPasswordValid = true,
                    isValidForm = _uiState.value.isEmailValid
                )
            }
        }
    }
}