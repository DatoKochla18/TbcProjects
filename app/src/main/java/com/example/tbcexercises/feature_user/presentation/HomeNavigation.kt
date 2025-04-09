package com.example.tbcexercises.feature_user.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tbcexercises.feature_user.presentation.home_screen.HomeScreenRoot
import kotlinx.serialization.Serializable

@Serializable
object Home

fun NavGraphBuilder.homeGraph() {

    composable<Home> {
        HomeScreenRoot()
    }
}