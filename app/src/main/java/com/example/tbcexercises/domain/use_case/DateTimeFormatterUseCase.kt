package com.example.tbcexercises.domain.use_case

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private val georgianMonths = mapOf(
    1 to "იან.",
    2 to "თებ.",
    3 to "მარ.",
    4 to "აპრ.",
    5 to "მაი.",
    6 to "ივნ.",
    7 to "ივლ.",
    8 to "აგვ.",
    9 to "სექ.",
    10 to "ოქტ.",
    11 to "ნოემ.",
    12 to "დეკ."
)

class DateTimeFormatterUseCase @Inject constructor() {

    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(input: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        val dateTime = LocalDateTime.parse(input, inputFormatter)


        val hourMinute = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        val day = dateTime.dayOfMonth
        val month = georgianMonths[dateTime.monthValue] ?: ""

        return "$hourMinute – $day/$month"
    }
}