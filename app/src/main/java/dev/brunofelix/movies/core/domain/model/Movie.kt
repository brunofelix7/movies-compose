package dev.brunofelix.movies.core.domain.model

import dev.brunofelix.movies.core.util.datetime.DateTimeConvert
import dev.brunofelix.movies.core.util.datetime.DateTimePatterns
import dev.brunofelix.movies.core.util.datetime.DateTimeResult

data class Movie(
    val id: Long = 0L,
    val title: String = "",
    val originalTitle: String = "",
    val originalLanguage: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val adult: Boolean = false,
    val genreIds: List<Int> = emptyList(),
    val popularity: Double = 0.0,
    val video: Boolean = false,
    val voteAverage: Float = 0f,
    val voteCount: Int = 0,
    val budget: Int = 0,
    val genres: List<MovieGenre> = emptyList(),
    val homepage: String = "",
    val imdbId: String = "",
    val originCountry: List<String> = emptyList(),
    val revenue: Long = 0L,
    val runtime: Int = 0,
    val status: String = "",
    val tagline: String = "",
    val duration: Int = 0
) {
    fun getReleaseDate(): DateTimeResult {
        return DateTimeConvert.format(
            value = releaseDate,
            fromPattern = DateTimePatterns.YYYY_MM_DD.pattern,
            toPattern = DateTimePatterns.DD_MM_YYYY.pattern
        )
    }

    var isVoteAverageVisible: Boolean = true

    fun setVoteAverageVisibility(isVisible: Boolean) {
        isVoteAverageVisible = isVisible
    }
}