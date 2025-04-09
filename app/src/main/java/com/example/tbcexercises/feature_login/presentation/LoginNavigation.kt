package com.example.tbcexercises.feature_login.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tbcexercises.core.presentation.extension.getValue
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginEvent
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginScreenRoot
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginViewModel
import com.example.tbcexercises.navigation.EMAIL
import com.example.tbcexercises.navigation.PASSWORD
import kotlinx.serialization.Serializable

@Serializable
data class Login(val email: String?, val password: String?)


fun NavGraphBuilder.loginGraph(
    navigateToHomeScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    scaffoldState: SnackbarHostState,
) {
    composable<Login> { entry ->
        val email = entry.getValue<String>(EMAIL)
        val password = entry.getValue<String>(PASSWORD)

        val viewModel: LoginViewModel = hiltViewModel()

        if (email != null && password != null) {
            viewModel.onEvent(LoginEvent.OnEmailChanged(email))
            viewModel.onEvent(LoginEvent.OnPasswordChanged(password))
        }

        LoginScreenRoot(
            viewModel = viewModel,
            navigateToHomeScreen = {
                navigateToHomeScreen()
            },
            navigateToRegisterScreen = {
                navigateToRegisterScreen()
            },
            scaffoldState = scaffoldState
        )
    }
}