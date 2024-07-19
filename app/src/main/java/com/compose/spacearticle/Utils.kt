package com.compose.spacearticle

import java.text.SimpleDateFormat
import java.util.Locale

object Utils {


    fun String.withDateFormat(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
        val date = inputFormat.parse(this) ?: return ""

        return outputFormat.format(date)
    }

    fun getFirstSentence(summary: String): String {
        val firstPeriodIndex = summary.indexOf('.')
        return if (firstPeriodIndex != -1) {
            summary.substring(0, firstPeriodIndex + 1)
        } else {
            summary
        }
    }
}