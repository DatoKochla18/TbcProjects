package com.example.tbcexercises.feauture_launcher.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tbcexercises.feauture_launcher.presentation.launcher_screen.LauncherScreen
import kotlinx.serialization.Serializable

@Serializable
object Launcher

fun NavGraphBuilder.launcherGraph(navigateToLogin: () -> Unit, navigateToHome: () -> Unit) {

    composable<Launcher> {
        LauncherScreen(
            navigateToHomeScreen = navigateToHome,
            navigateToLoginScreen = navigateToLogin
        )
    }
}