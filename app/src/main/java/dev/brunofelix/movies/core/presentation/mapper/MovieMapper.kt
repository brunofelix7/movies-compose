package dev.brunofelix.movies.core.presentation.mapper

import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.presentation.state.MovieUiState
import dev.brunofelix.movies.core.domain.util.datetime.DateTimeConvert
import dev.brunofelix.movies.core.domain.util.datetime.DateTimePatterns

/**
 * Maps a [Movie] domain model to a [MovieUiState] for the presentation layer.
 *
 * This function performs UI-specific transformations, such as converting the
 * release date from "YYYY-MM-DD" to "DD/MM/YYYY" format for display.
 *
 * @return A [MovieUiState] containing formatted data for the UI.
 */
fun Movie.toUiState(): MovieUiState {
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
        genres = genres
    )
}