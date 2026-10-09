package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.data.remote.dto.movie.MovieGenreDto
import dev.brunofelix.movies.domain.model.MovieGenre

/**
 * Maps a [MovieGenreDto] to a [MovieGenre] domain model.
 *
 * Provides fallbacks for missing IDs (-1) or names ("--").
 *
 * @return A [MovieGenre] domain object.
 */
fun MovieGenreDto.toDomain(): MovieGenre {
    return MovieGenre(
        id = id ?: -1,
        name = name ?: "--"
    )
}
