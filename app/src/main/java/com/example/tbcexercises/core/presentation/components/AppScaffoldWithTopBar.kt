@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.tbcexercises.core.presentation.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tbcexercises.navigation.NavigationConfig
import com.example.tbcexercises.navigation.NavigationRoutes
import com.example.tbcexercises.R
import com.example.tbcexercises.core.presentation.resource.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(currentRoute: String, onBackClicked: () -> Unit) {
    //val navBackStackEntry by navController.currentBackStackEntryAsState()
    //val currentRoute = navBackStackEntry?.destination?.route ?: return

    Log.d("current route", currentRoute)
    //  Log.d("currentrouteobj", navBackStackEntry?.destination?.toString() ?: "")

    val currentScreen = when {
        currentRoute.contains("NavigationRoutes.Auth.Login") -> NavigationRoutes.Auth.Login::class
        currentRoute.contains("NavigationRoutes.Auth.Register") -> NavigationRoutes.Auth.Register
        currentRoute.contains("NavigationRoutes.Main.Launcher") -> NavigationRoutes.Main.Launcher
        currentRoute.contains("NavigationRoutes.Main.Home") -> NavigationRoutes.Main.Home
        currentRoute.contains("NavigationRoutes.Main.Profile") -> NavigationRoutes.Main.Profile
        else -> null
    }

    if (currentScreen !in NavigationConfig.hideTopAppBar) {
        val title = currentScreen?.let { NavigationConfig.screenTitles[it] } ?: ""
        val showBackButton = currentScreen in NavigationConfig.showBackButton


        CenterAlignedTopAppBar(title = {
            Text(
                text = title,
                color = Colors.BLACK,
                maxLines = 1,
            )
        }, navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { onBackClicked() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = Color.Black
                    )
                }
            }
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Gray
        )
        )
    }

}

@Preview
@Composable
fun AppTopBarPreview() {
    AppTopBar("com.example.tbcexercises.NavigationRoutes.Auth.Register", {})
}