package dev.brunofelix.movies.core.domain.util.datetime

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Utility for converting and formatting date and time strings across different patterns and time zones.
 *
 * Uses the modern java.time API (Available from API 26+).
 */
object DateTimeConverter {

    /** Common date patterns used in the application. */
    const val YYYY_MM_DD = "yyyy-MM-dd"
    const val DD_MM_YYYY = "dd/MM/yyyy"

    /**
     * Converts a date string from one pattern to another, with optional time zone support.
     *
     * @param value The date string to convert.
     * @param fromPattern The pattern of the input string.
     * @param toPattern The desired output pattern.
     * @param fromZone The time zone of the input date (defaults to system default).
     * @param toZone The time zone for the output date (defaults to system default).
     * @return A [Result] object containing the formatted string and other date representations.
     */
    fun format(
        value: String,
        fromPattern: String,
        toPattern: String,
        fromZone: ZoneId = ZoneId.systemDefault(),
        toZone: ZoneId = ZoneId.systemDefault()
    ): Result {
        return try {
            val formatterInput = DateTimeFormatter.ofPattern(fromPattern, Locale.getDefault())
            val formatterOutput = DateTimeFormatter.ofPattern(toPattern, Locale.getDefault())

            // We use LocalDate if the pattern only contains date components, 
            // or LocalDateTime/ZonedDateTime if it contains time.
            // For TMDB's "yyyy-MM-dd", LocalDate is sufficient.
            val localDate = LocalDate.parse(value, formatterInput)
            val zonedDateTime = localDate.atStartOfDay(fromZone).withZoneSameInstant(toZone)

            Result(
                value = zonedDateTime.format(formatterOutput),
                timestamp = zonedDateTime.toInstant().toEpochMilli(),
                localDateTime = zonedDateTime.toLocalDateTime()
            )
        } catch (_: Exception) {
            Result()
        }
    }

    /**
     * Converts a timestamp to a formatted date string, with optional time zone support.
     *
     * @param timestamp The epoch millisecond timestamp to convert.
     * @param toPattern The desired output pattern.
     * @param toZone The time zone for the output date (defaults to system default).
     * @return A [Result] object containing the formatted string and other date representations.
     */
    fun format(
        timestamp: Long,
        toPattern: String,
        toZone: ZoneId = ZoneId.systemDefault()
    ): Result {
        return try {
            val formatterOutput = DateTimeFormatter.ofPattern(toPattern, Locale.getDefault())
            val instant = Instant.ofEpochMilli(timestamp)
            val zonedDateTime = instant.atZone(toZone)

            Result(
                value = zonedDateTime.format(formatterOutput),
                timestamp = timestamp,
                localDateTime = zonedDateTime.toLocalDateTime()
            )
        } catch (_: Exception) {
            Result()
        }
    }

    /**
     * Data class representing the multiple formats of a converted date.
     *
     * @property value The formatted date string (e.g., "13/08/2024"). Defaults to "--".
     * @property timestamp The epoch millisecond representation of the date.
     * @property localDateTime The [LocalDateTime] representation of the date.
     */
    data class Result(
        val value: String = "--",
        val timestamp: Long = 0L,
        val localDateTime: LocalDateTime = LocalDateTime.now()
    )
}
