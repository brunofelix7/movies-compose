package dev.brunofelix.movies.core.domain.model

import dev.brunofelix.movies.core.util.datetime.DateTimeConvert
import dev.brunofelix.movies.core.util.datetime.DateTimeResult

data class MovieDetails(
    val genres: List<Genre>? = emptyList(),
    val overview: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val duration: Int = 0,
    val voteCount: Int = 0,
) {
    fun getReleaseDate(): DateTimeResult {
        return DateTimeConvert.format(
            value = releaseDate,
            fromPattern = "yyyy-MM-dd",
            toPattern = "dd/MM/yyyy"
        )
    }
}