package com.example.tbcexercises

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Long.toDate(): String {


    val date = Date(this   )
    val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return dateFormat.format(date)
}