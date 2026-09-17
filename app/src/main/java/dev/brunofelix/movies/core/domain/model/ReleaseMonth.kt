package dev.brunofelix.movies.core.domain.model

import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * A single month of the release calendar.
 *
 * [id] is the `yyyy-MM` form used to carry the month across navigation, and [startDate] /
 * [endDate] are the inclusive `yyyy-MM-dd` bounds the TMDB discover endpoints expect.
 */
data class ReleaseMonth(
    val year: Int,
    val month: Int
) {
    private val yearMonth: YearMonth
        get() = YearMonth.of(year, month)

    val id: String
        get() = yearMonth.format(ID_FORMATTER)

    val startDate: String
        get() = yearMonth.atDay(1).format(DATE_FORMATTER)

    val endDate: String
        get() = yearMonth.atEndOfMonth().format(DATE_FORMATTER)

    /**
     * Short month name, with the year appended when it is not the current one.
     */
    fun label(locale: Locale = Locale.getDefault()): String {
        val name = yearMonth.month
            .getDisplayName(java.time.format.TextStyle.SHORT, locale)
            .replaceFirstChar { it.titlecase(locale) }
            .removeSuffix(".")
        return if (year == YearMonth.now().year) name else "$name $year"
    }

    companion object {
        private val ID_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM")
        private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        fun current(): ReleaseMonth = YearMonth.now().toReleaseMonth()

        fun from(id: String?): ReleaseMonth? {
            return runCatching { YearMonth.parse(id, ID_FORMATTER).toReleaseMonth() }.getOrNull()
        }

        /**
         * Rolling window centred on the current month.
         */
        fun window(monthsBack: Int, monthsForward: Int): List<ReleaseMonth> {
            val now = YearMonth.now()
            return (-monthsBack..monthsForward).map { offset ->
                now.plusMonths(offset.toLong()).toReleaseMonth()
            }
        }

        private fun YearMonth.toReleaseMonth() = ReleaseMonth(year = year, month = monthValue)
    }
}
