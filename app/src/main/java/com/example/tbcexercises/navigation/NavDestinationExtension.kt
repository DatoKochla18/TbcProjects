package com.example.tbcexercises.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute

fun <T : Any> NavDestination?.matchesAnyRoute(routes: Collection<T>): Boolean {
    return this?.let { destination ->
        routes.any { route ->
            destination.hasRoute(route::class)
        }
    } ?: false
}