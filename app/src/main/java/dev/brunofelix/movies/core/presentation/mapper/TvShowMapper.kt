package dev.brunofelix.movies.core.presentation.mapper

import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.util.datetime.DateTimeConverter
import dev.brunofelix.movies.core.presentation.state.TvShowUiState

/**
 * Maps a [TvShow] domain model to a [TvShowUiState] for the presentation layer.
 *
 * This function performs UI-specific transformations, such as converting the
 * first air date from "YYYY-MM-DD" to "DD/MM/YYYY" format for display.
 *
 * @return A [TvShowUiState] containing formatted data for the UI.
 */
fun TvShow.toUiState(): TvShowUiState {
    return TvShowUiState(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        firstAirDate = DateTimeConverter.format(
            value = firstAirDate,
            fromPattern = DateTimeConverter.YYYY_MM_DD,
            toPattern = DateTimeConverter.DD_MM_YYYY
        ).value,
        voteAverage = voteAverage,
        genres = genres,
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons
    )
}
