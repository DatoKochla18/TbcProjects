package com.example.tbcexercises.core.presentation.resource

import android.graphics.Color


fun parseHexColor(hex: String): androidx.compose.ui.graphics.Color {
    return androidx.compose.ui.graphics.Color(Color.parseColor(hex))
}


object Colors {

    val WHITE = parseHexColor("#FFFFFF")
    val BLACK = parseHexColor("#000000")
    val GRAY = parseHexColor("#666666")
    val CYAN = parseHexColor("#63cdf7")
    val PURPLE = parseHexColor("#840991")
    val RED = parseHexColor("#f50515")
}