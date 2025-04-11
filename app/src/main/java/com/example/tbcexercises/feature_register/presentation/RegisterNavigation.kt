package com.example.tbcexercises.feature_register.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tbcexercises.feature_register.presentation.register_screen.RegisterRootScreen
import kotlinx.serialization.Serializable


@Serializable
object Register


fun NavGraphBuilder.registerGraph(
    snackBarHostState: SnackbarHostState,
    onRegister: (String, String) -> Unit,
) {

    composable<Register> {
        RegisterRootScreen(snackBarHostState = snackBarHostState) { email, password ->
            onRegister(email, password)
        }
    }
}