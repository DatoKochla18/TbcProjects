package com.example.tbcexercises.feature_login.presentation.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcexercises.core.domain.util.PreferenceKeys.REMEMBER_ME_KEY
import com.example.tbcexercises.core.domain.util.Resource
import com.example.tbcexercises.core.presentation.extension.asStringResource
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
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    private val _uiEventChannel = Channel<LoginSideEffect>()
    val uiEvents = _uiEventChannel.receiveAsFlow()




    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login(event.email, event.password)

            LoginEvent.SwitchCheckBoxStatus -> uiState =
                uiState.copy(rememberMe = !uiState.rememberMe)

            LoginEvent.SwitchShowPasswordStatus -> uiState =
                uiState.copy(showPassword = !uiState.showPassword)

            is LoginEvent.OnEmailChanged -> {
                uiState = uiState.copy(email = event.email)
                viewModelScope.launch {
                    val result = loginUseCaseWrapper.validateEmailUseCase(event.email)
                    uiState = when (result) {
                        is Resource.Error -> uiState.copy(emailError = result.error)
                        is Resource.Success -> uiState.copy(
                            emailError = null,
                            isEmailValid = true,
                            isValidForm = uiState.isPasswordValid
                        )
                    }
                }
            }

            is LoginEvent.OnPasswordChanged -> {
                uiState = uiState.copy(password = event.password)
                viewModelScope.launch {
                    val result = loginUseCaseWrapper.validatePasswordUseCase(event.password)
                    uiState = when (result) {
                        is Resource.Error -> uiState.copy(passwordError = result.error)
                        is Resource.Success -> uiState.copy(
                            passwordError = null,
                            isPasswordValid = true,
                            isValidForm = uiState.isEmailValid
                        )
                    }
                }
            }

            is LoginEvent.GetResultFromRegister -> uiState =
                uiState.copy(email = event.email, password = event.password)
        }
    }


    private fun login(email: String, password: String) {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            loginUseCaseWrapper.loginUseCase(email, password).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        uiState = uiState.copy(isLoading = false)
                        _uiEventChannel.send(LoginSideEffect.ShowSnackBar(result.error.asStringResource()))
                    }

                    is Resource.Success -> {
                        if (uiState.rememberMe) {
                            saveUserCredentials()
                        }
                        uiState = uiState.copy(isLoading = false)
                        _uiEventChannel.send(LoginSideEffect.SuccessFullLogin)
                    }
                }
            }
        }
    }

    private fun saveUserCredentials() {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCaseWrapper.saveValueToLocalStorageUseCase(REMEMBER_ME_KEY, uiState.rememberMe)
        }
    }
}