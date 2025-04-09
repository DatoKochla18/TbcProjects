package com.example.tbcexercises.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.tbcexercises.feature_login.presentation.Login
import com.example.tbcexercises.feature_profile.presentation.Profile
import com.example.tbcexercises.feature_profile.presentation.profileGraph
import com.example.tbcexercises.feature_user.presentation.Home
import com.example.tbcexercises.feature_user.presentation.homeGraph
import com.example.tbcexercises.feauture_launcher.presentation.Launcher
import com.example.tbcexercises.feauture_launcher.presentation.launcherGraph

fun NavGraphBuilder.mainNavigation(navController: NavHostController) {


    launcherGraph(
        navigateToLogin = {
            navController.navigate(
                Login(null, null)
            ) { popUpTo<Launcher> { inclusive = true } }
        },
        navigateToHome = {
            navController.navigate(Home) {
                popUpTo<Launcher> { inclusive = true }
            }
        }
    )

    homeGraph()

    profileGraph {
        navController.navigate(Login(email = null, password = null)) {
            popUpTo<Home> {
                inclusive = true
            }
        }
    }
}
