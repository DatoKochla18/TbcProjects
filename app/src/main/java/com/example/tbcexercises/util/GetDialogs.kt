package com.example.tbcexercises.util

import com.example.tbcexercises.R
import com.example.tbcexercises.data.model.Dialog
import com.example.tbcexercises.data.model.DialogType


fun getDialogs(): List<Dialog> {
    return listOf(
        Dialog(1, 1, DialogType.NUMBER),
        Dialog(2, 2, DialogType.NUMBER),
        Dialog(3, 3, DialogType.NUMBER),
        Dialog(4, 4, DialogType.NUMBER),
        Dialog(5, 5, DialogType.NUMBER),
        Dialog(6, 6, DialogType.NUMBER),
        Dialog(7, 7, DialogType.NUMBER),
        Dialog(8, 8, DialogType.NUMBER),
        Dialog(9, 9, DialogType.NUMBER),
        Dialog(10, R.drawable.fingerprint, DialogType.FINGERPRINT),
        Dialog(0, 0, DialogType.NUMBER),
        Dialog(11, R.drawable.backspace, DialogType.BACKSPACE),
    )
}