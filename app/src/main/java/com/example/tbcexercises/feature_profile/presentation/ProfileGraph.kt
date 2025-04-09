package com.example.tbcexercises.feature_profile.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tbcexercises.feature_profile.presentation.profile_screen.ProfileScreenRoot
import kotlinx.serialization.Serializable

@Serializable
object Profile

fun NavGraphBuilder.profileGraph(navigateToLogin: () -> Unit) {
    composable<Profile> {
        ProfileScreenRoot {
            navigateToLogin()
        }
    }
}