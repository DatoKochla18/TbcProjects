package com.example.tbcexercises.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.tbcexercises.core.presentation.extension.getValue
import com.example.tbcexercises.core.presentation.extension.setValue
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginEvent
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginScreenRoot
import com.example.tbcexercises.feature_login.presentation.login_screen.LoginViewModel
import com.example.tbcexercises.feature_register.presentation.register_screen.RegisterRootScreen
import com.example.tbcexercises.feauture_launcher.presentation.launcher_screen.LauncherScreen

fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    scaffoldState: SnackbarHostState,
) {

    val EMAIL = "email"
    val PASSWORD = "password"


    composable<NavigationRoutes.Auth.Login> { entry ->
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
                navController.navigate(NavigationRoutes.Main.Home) {
                    popUpTo<NavigationRoutes.Auth.Login> { inclusive = true }
                }
            },
            navigateToRegisterScreen = {
                navController.navigate(NavigationRoutes.Auth.Register)
            },
            scaffoldState = scaffoldState
        )
    }

    composable<NavigationRoutes.Auth.Register> {
        RegisterRootScreen(scaffoldState = scaffoldState) { email, password ->
            navController.apply {
                previousBackStackEntry?.setValue(EMAIL, email)
                previousBackStackEntry?.setValue(PASSWORD, password)
            }
            navController.popBackStack()
        }
    }
}