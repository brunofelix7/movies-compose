package dev.brunofelix.movies.core.util.datetime

enum class DateTimePatterns(
    val pattern: String
) {
    YYYY_MM_DD("yyyy-MM-dd"),
    DD_MM_YYYY("dd/MM/yyyy")
}