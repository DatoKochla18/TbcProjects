package com.example.tbcexercises.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.tbcexercises.feature_profile.presentation.profile_screen.ProfileScreenRoot
import com.example.tbcexercises.feature_user.presentation.home_screen.HomeScreenRoot
import com.example.tbcexercises.feauture_launcher.presentation.launcher_screen.LauncherScreen

fun NavGraphBuilder.mainNavigation(navController: NavHostController) {


    composable<NavigationRoutes.Main.Launcher> {
        LauncherScreen(
            navigateToLoginScreen = {
                navController.navigate(
                    NavigationRoutes.Auth.Login(null, null)
                ) { popUpTo<NavigationRoutes.Main.Launcher> { inclusive = true } }
            },
            navigateToHomeScreen = {
                navController.navigate(NavigationRoutes.Main.Home) {
                    popUpTo<NavigationRoutes.Main.Launcher> { inclusive = true }
                }
            }
        )
    }


    composable<NavigationRoutes.Main.Home> {
        HomeScreenRoot(
            navigateToProfileScreen = {
                navController.navigate(NavigationRoutes.Main.Profile)
            }
        )
    }

    composable<NavigationRoutes.Main.Profile> {
        ProfileScreenRoot {
            navController.navigate(NavigationRoutes.Auth.Login(email = null, password = null)) {
                popUpTo<NavigationRoutes.Main.Home> {
                    inclusive = true
                }
            }
        }
    }
}
