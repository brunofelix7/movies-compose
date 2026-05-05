package dev.brunofelix.movies.core.presentation.mapper

import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.util.datetime.DateTimeConvert
import dev.brunofelix.movies.core.util.datetime.DateTimePatterns

fun Movie.toUiState(
    isVoteVisible: Boolean = true
): MovieUiState {
    return MovieUiState(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = DateTimeConvert.format(
            value = releaseDate,
            fromPattern = DateTimePatterns.YYYY_MM_DD.pattern,
            toPattern = DateTimePatterns.DD_MM_YYYY.pattern
        ).value,
        voteAverage = voteAverage,
        duration = duration,
        genres = genres,
        isVoteAverageVisible = isVoteVisible
    )
}