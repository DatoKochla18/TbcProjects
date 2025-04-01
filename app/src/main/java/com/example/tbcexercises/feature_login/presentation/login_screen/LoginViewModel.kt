package com.example.tbcexercises.feature_login.presentation.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.core.domain.util.PreferenceKeys.REMEMBER_ME_KEY
import com.example.tbcexercises.core.domain.util.Result
import com.example.tbcexercises.feature_login.domain.use_case.LoginUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCaseWrapper: LoginUseCaseWrapper,
) :
    ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun saveRememberMe(rememberMe: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCaseWrapper.saveValueToLocalStorageUseCase(REMEMBER_ME_KEY, rememberMe)
        }
    }

    private val _uiEventChannel = Channel<LoginSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    init {
        snapshotFlow { uiState.email }
            .drop(1)
            .map { loginUseCaseWrapper.validateEmailUseCase(it) }
            .onEach { result ->
                uiState = when (result) {
                    is Result.Error -> uiState.copy(emailError = result.error)
                    is Result.Success -> uiState.copy(
                        emailError = null,
                        isEmailValid = true,
                        isValidForm = uiState.isPasswordValid
                    )
                }
            }
            .launchIn(viewModelScope)

        snapshotFlow { uiState.password }
            .drop(1)
            .map { loginUseCaseWrapper.validatePasswordUseCase(it) }
            .onEach { result ->
                uiState = when (result) {
                    is Result.Error -> uiState.copy(passwordError = result.error)
                    is Result.Success -> uiState.copy(
                        passwordError = null,
                        isPasswordValid = true,
                        isValidForm = uiState.isEmailValid
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login(event.email, event.password)
            LoginEvent.SwitchCheckBoxStatus -> uiState =
                uiState.copy(rememberMe = !uiState.rememberMe)

            LoginEvent.SwitchShowPasswordStatus -> uiState =
                uiState.copy(showPassword = !uiState.showPassword)

            is LoginEvent.OnEmailChanged -> uiState = uiState.copy(email = event.email)
            is LoginEvent.OnPasswordChanged -> uiState = uiState.copy(password = event.password)
        }
    }

    private fun login(email: String, password: String) {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            loginUseCaseWrapper.loginUseCase(email, password).collect { result ->
                when (result) {
                    is Result.Error -> {
                        uiState = uiState.copy(isLoading = false)
                        _uiEventChannel.send(LoginSideEffect.ShowSnackBar(result.error))
                    }

                    is Result.Success -> {
                        _uiEventChannel.send(LoginSideEffect.SuccessFullLogin)
                    }
                }
            }
        }
    }
}