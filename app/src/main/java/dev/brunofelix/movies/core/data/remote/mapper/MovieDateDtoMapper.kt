package dev.brunofelix.movies.core.data.remote.mapper

import dev.brunofelix.movies.core.data.remote.dto.movie.MovieDateDto
import dev.brunofelix.movies.core.domain.model.MovieDate

/**
 * Maps a [MovieDateDto] to a [MovieDate] domain model.
 *
 * @return A [MovieDate] object with non-nullable maximum and minimum dates.
 */
fun MovieDateDto.toDomain(): MovieDate {
    return MovieDate(
        maximum = maximum.orEmpty(),
        minimum = minimum.orEmpty()
    )
}
