package dev.brunofelix.movies.core.presentation.mapper

import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.presentation.ui.model.MediaUiModel
import dev.brunofelix.movies.core.presentation.ui.model.MovieUiModel

/**
 * Maps a [Media] domain model to a [MovieUiModel] for the presentation layer.
 *
 * @return A [MovieUiModel] containing formatted data for the UI.
 */
fun Media.toUiModel() = MediaUiModel(
    id = id,
    title = title,
    posterPath = posterPath,
    type = type
)