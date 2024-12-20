package com.example.tbcexercises

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun String.toTextDate(): String {
    var result = ""
    try {
        val longFormat = this.toLong()
        val date = Date(longFormat)
        val format = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        result = format.format(date).toString()
    } catch (e: Exception) {
        result = ""
    }
    return result
}