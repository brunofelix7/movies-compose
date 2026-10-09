package dev.brunofelix.movies.presentation.mapper

import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.presentation.model.CastUiModel

/**
 * Maps a [Cast] domain model to a [CastUiModel] for the presentation layer.
 *
 * @return A [CastUiModel] containing the data shown on the cast card.
 */
fun Cast.toUiModel(): CastUiModel {
    return CastUiModel(
        id = id,
        name = name,
        character = character,
        profilePath = profilePath
    )
}
