package com.example.tbcexercises.core.presentation.extension

import androidx.navigation.NavBackStackEntry


fun <T> NavBackStackEntry.setValue(key: String, value: T) {
    this.savedStateHandle[key] = value
}


fun <T> NavBackStackEntry.getValue(key: String): T? {
    return this.savedStateHandle.get<T>(key)
}
