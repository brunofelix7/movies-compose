package dev.brunofelix.movies.core.util.datetime

import java.time.LocalDateTime
import java.util.Date

data class DateTimeResult(
    val value: String = "--",
    val timeStamp: Long = 0L,
    val date: Date = Date(),
    val localDateTime: LocalDateTime = LocalDateTime.now()
)