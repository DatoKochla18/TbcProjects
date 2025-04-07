package com.example.tbcexercises.navigation

import kotlinx.serialization.Serializable

sealed class NavigationRoutes {

    sealed class Auth {

        @Serializable
        data class Login(val email: String?, val password: String?) : NavigationRoutes()

        @Serializable
        object Register : NavigationRoutes()
    }

    sealed class Main {
        @Serializable
        object Launcher : NavigationRoutes()

        @Serializable
        object Home : NavigationRoutes()

        @Serializable
        object Profile : NavigationRoutes()
    }
}