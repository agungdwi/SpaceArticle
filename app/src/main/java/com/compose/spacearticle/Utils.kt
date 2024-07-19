package com.compose.spacearticle

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object Utils {


    fun String.withDateFormat(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())

        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = inputFormat.parse(this) ?: return ""

        outputFormat.timeZone = TimeZone.getDefault()

        return outputFormat.format(date)
    }

    fun getFirstSentence(summary: String): String {
        var firstPeriodIndex = -1

        for (i in summary.indices) {
            if (summary[i] == '.') {
                if (i == summary.length - 1 || summary[i + 1].isWhitespace() ) {
                    firstPeriodIndex = i
                    break
                }
            }
        }
        return if (firstPeriodIndex != -1) {
            summary.substring(0, firstPeriodIndex + 1)
        } else {
            summary
        }
    }
}