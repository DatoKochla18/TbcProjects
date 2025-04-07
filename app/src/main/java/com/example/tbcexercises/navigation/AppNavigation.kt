package com.example.tbcexercises.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tbcexercises.core.presentation.components.AppTopBar


@Composable
fun AppNavigation(
    navController: NavHostController,
    scaffoldState: SnackbarHostState,
) {
    //if i did not specify this the navigation will not work
    // because at start  current route is null
    val starter = "com.example.tbcexercises.navigation.NavigationRoutes.Main.Launcher"

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Log.d("executed", "${navBackStackEntry?.destination?.route}")

    val currentRoute = navBackStackEntry?.destination?.route ?: starter

    Log.d("currentRoute", currentRoute)

    Scaffold(
        topBar = { AppTopBar(currentRoute) { navController.popBackStack() } },
        snackbarHost = { SnackbarHost(hostState = scaffoldState) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.Main.Launcher,
            modifier = Modifier.padding(innerPadding)
        ) {
            authNavigation(navController, scaffoldState)

            mainNavigation(navController)
        }
    }
}