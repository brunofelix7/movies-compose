package dev.brunofelix.movies.core.util.datetime

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateTimeConvert {

    fun format(
        value: String,
        fromPattern: String,
        fromTimeZone: String = TimeZone.getDefault().id,
        toPattern: String,
        toTimeZone: String = TimeZone.getDefault().id
    ): DateTimeResult {
        try {
            val fromFormatter = SimpleDateFormat(fromPattern, Locale.getDefault())
            val toFormatter = SimpleDateFormat(toPattern, Locale.getDefault())
            fromFormatter.timeZone = TimeZone.getTimeZone(fromTimeZone)
            toFormatter.timeZone = TimeZone.getTimeZone(TimeZone.getTimeZone(toTimeZone).id)
            val date = fromFormatter.parse(value) ?: Date()
            return DateTimeResult(
                value = toFormatter.format(date),
                timeStamp = toFormatter.parse(toFormatter.format(date)).time,
                date = date,
                localDateTime = date.toInstant()
                    .atZone(TimeZone.getTimeZone(toTimeZone).toZoneId())
                    .toLocalDateTime()
            )
        } catch (_: Exception) {
            return DateTimeResult()
        }
    }
}