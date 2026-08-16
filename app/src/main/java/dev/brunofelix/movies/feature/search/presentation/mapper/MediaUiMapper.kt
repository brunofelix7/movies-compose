package dev.brunofelix.movies.feature.search.presentation.mapper

import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.presentation.state.MovieUiState

/**
 * Maps a [Media] domain model to a [MovieUiState] for the presentation layer.
 *
 * @return A [MovieUiState] containing formatted data for the UI.
 */
fun Media.toUiState() = MovieUiState(
    id = id,
    title = title,
    posterPath = posterPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    overview = overview
)
