package com.example.baseproject.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateHelper {

    fun convertEpochToReadableFormat(epochTime: Long): String {
        val date = Date(epochTime * 1000)

        val dateFormat = SimpleDateFormat("d MMMM 'at' h:mm a", Locale.getDefault())

        dateFormat.timeZone = TimeZone.getDefault()

        return dateFormat.format(date)
    }

}