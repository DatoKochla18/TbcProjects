package com.example.tbcexercises.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.tbcexercises.core.presentation.extension.setValue
import com.example.tbcexercises.feature_login.presentation.Login
import com.example.tbcexercises.feature_login.presentation.loginGraph
import com.example.tbcexercises.feature_register.presentation.Register
import com.example.tbcexercises.feature_register.presentation.registerGraph
import com.example.tbcexercises.feature_user.presentation.Home

const val EMAIL = "email"
const val PASSWORD = "password"

fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    scaffoldState: SnackbarHostState,
) {


    loginGraph(
        navigateToHomeScreen = {
            navController.navigate(Home) {
                popUpTo<Login> { inclusive = true }
            }
        },
        navigateToRegisterScreen = {
            navController.navigate(Register)

        },
        scaffoldState = scaffoldState
    )

    registerGraph(
        scaffoldState
    ) { email, password ->
        navController.apply {
            previousBackStackEntry?.setValue(EMAIL, email)
            previousBackStackEntry?.setValue(PASSWORD, password)
        }
        navController.popBackStack()
    }

}



