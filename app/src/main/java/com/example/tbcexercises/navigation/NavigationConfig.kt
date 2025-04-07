package com.example.tbcexercises.navigation

object NavigationConfig {

    val hideTopAppBar = setOf(
        NavigationRoutes.Main.Launcher
    )

    val showBackButton = setOf(
        NavigationRoutes.Auth.Register,
        NavigationRoutes.Main.Profile
    )

    val screenTitles = mapOf(
        NavigationRoutes.Main.Launcher to "Welcome",
        NavigationRoutes.Auth.Login::class to "Login",
        NavigationRoutes.Auth.Register to "Register",
        NavigationRoutes.Main.Home to "Home",
        NavigationRoutes.Main.Profile to "Profile"
    )
}