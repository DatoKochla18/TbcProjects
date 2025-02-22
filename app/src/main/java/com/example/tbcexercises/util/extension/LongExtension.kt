package com.example.tbcexercises.util.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDateString(): String {
    val date = Date(this)
    val format = SimpleDateFormat("d MMMM 'at' h:mm a", Locale.ENGLISH)
    return format.format(date)
}