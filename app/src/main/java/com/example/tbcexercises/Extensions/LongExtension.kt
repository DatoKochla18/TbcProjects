package com.example.tbcexercises.Extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Long.toDate(): String {
    val currentDate = Calendar.getInstance()
    val eventDate =
        Calendar.getInstance().apply { timeInMillis = this@toDate }

    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val isToday = currentDate.get(Calendar.YEAR) == eventDate.get(Calendar.YEAR) &&
            currentDate.get(Calendar.DAY_OF_YEAR) == eventDate.get(Calendar.DAY_OF_YEAR)

    return if (isToday) {
        "Today, ${timeFormat.format(eventDate.time)}"
    } else {
        val dateFormat = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
        dateFormat.format(eventDate.time)
    }
}
