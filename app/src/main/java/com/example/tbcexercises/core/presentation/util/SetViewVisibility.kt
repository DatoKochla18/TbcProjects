package com.example.tbcexercises.core.presentation.util

import android.view.View
import androidx.core.view.isVisible

fun setViewsVisibility(
    isLoading: Boolean,
    progressBar: View,
    vararg viewsToHideWhenLoading: View,
) {
    progressBar.isVisible = isLoading

    viewsToHideWhenLoading.forEach { view ->
        view.isVisible = !isLoading
    }
}