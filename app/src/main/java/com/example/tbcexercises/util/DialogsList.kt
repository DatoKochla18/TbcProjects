package com.example.tbcexercises.util

import com.example.tbcexercises.data.model.Dialog

fun getDialogData(): MutableList<Dialog> {
    return mutableListOf(

        Dialog("1", ""),
        Dialog("2", "ABC"),
        Dialog("3", "DEF"),
        Dialog("4", "GHI"),
        Dialog("5", "JKL"),
        Dialog("6", "MNO"),
        Dialog("7", "PQRS"),
        Dialog("8", "TUV"),
        Dialog("9", "WXYZ"),
        Dialog("#", ""),
        Dialog("0", "+"),
        Dialog("*", "")
    )
}