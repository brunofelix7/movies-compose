package dev.brunofelix.movies.core.domain.model

import dev.brunofelix.movies.core.util.datetime.DateTimeConvert
import dev.brunofelix.movies.core.util.datetime.DateTimePatterns
import dev.brunofelix.movies.core.util.datetime.DateTimeResult

data class MovieDetails(
    val genres: List<MovieGenre>? = emptyList(),
    val overview: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val duration: Int = 0,
    val voteCount: Int = 0,
) {
    fun getReleaseDate(): DateTimeResult {
        return DateTimeConvert.format(
            value = releaseDate,
            fromPattern = DateTimePatterns.YYYY_MM_DD.pattern,
            toPattern = DateTimePatterns.DD_MM_YYYY.pattern
        )
    }
}