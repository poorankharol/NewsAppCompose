package com.compose.newsapp.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object Utils {
    fun getFormattedDate(
        timestamp: String,
    ): String {
        val timestampFormat = "yyyy-MM-dd'T'HH:mm:SS'Z'"
        val outputFormat = "EEEE, dd MMM yyyy"

        val dateFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getTimeZone("GMT")

        val parser = SimpleDateFormat(timestampFormat, Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("GMT")

        try {
            val date = parser.parse(timestamp)
            if (date != null) {
                dateFormatter.timeZone = TimeZone.getDefault()
                return dateFormatter.format(date)
            }
        } catch (e: Exception) {
            // Handle parsing error
            e.printStackTrace()
        }

        // If parsing fails, return the original timestamp
        return timestamp
    }
}