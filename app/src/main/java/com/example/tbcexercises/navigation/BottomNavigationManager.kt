package com.example.tbcexercises.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tbcexercises.feature_profile.presentation.Profile
import com.example.tbcexercises.feature_user.presentation.Home

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)


object BottomNavigationManager {

    val toShowBottomNavList = listOf(
        Home, Profile
    )

    val topLevelRoutes = listOf(
        TopLevelRoute("Home", Home, Icons.Default.Home),
        TopLevelRoute("Profile", Profile, Icons.Default.Person),

        )
}