package com.example.tbcexercises.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tbcexercises.core.presentation.components.AppTopBar
import com.example.tbcexercises.feauture_launcher.presentation.Launcher
import com.example.tbcexercises.navigation.BottomNavigationManager.topLevelRoutes


@Composable
fun AppNavigation(
    navController: NavHostController,
    scaffoldState: SnackbarHostState,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val toShowBottomBar =
        currentDestination.matchesAnyRoute(BottomNavigationManager.toShowBottomNavList)
    val toHideAppBar = currentDestination.matchesAnyRoute(TopAppBarManager.hideTopAppBar)
    val toShowAppBarBackButton = currentDestination.matchesAnyRoute(TopAppBarManager.showBackButton)


    Scaffold(
        topBar = {
            if (!toHideAppBar) AppTopBar(
                currentDestination?.route.asScreenTitle(),
                toShowAppBarBackButton
            ) { navController.popBackStack() }
        },
        snackbarHost = { SnackbarHost(hostState = scaffoldState) },
        bottomBar = {
            if (toShowBottomBar) {
                NavigationBar {
                    topLevelRoutes.forEach { topLevelRoute ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = topLevelRoute.icon,
                                    contentDescription = topLevelRoute.name
                                )
                            },
                            label = { Text(topLevelRoute.name) },
                            selected = currentDestination?.hierarchy?.any {
                                it.hasRoute(topLevelRoute.route::class)
                            } == true,
                            onClick = {
                                navController.navigate(topLevelRoute.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Launcher,
            modifier = Modifier.padding(innerPadding)
        ) {
            authNavigation(navController, scaffoldState)

            mainNavigation(navController)
        }
    }
}

fun String?.asScreenTitle(): String {
    return this?.substringAfterLast(".")?.substringBefore("/") ?: ""

}
