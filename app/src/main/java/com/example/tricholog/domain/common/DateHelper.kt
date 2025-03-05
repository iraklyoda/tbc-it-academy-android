package com.example.tricholog.domain.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {
    fun convertsMillisToDate(millis: Long): String {
        val date = Date(millis)
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return format.format(date)
    }

    fun convertMillisToTime(millis: Long): String {
        val date = Date(millis)
        val timeFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        return timeFormat.format(date)
    }
}