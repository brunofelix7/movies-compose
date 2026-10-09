package dev.brunofelix.movies.data.remote.mapper

import dev.brunofelix.movies.data.remote.dto.movie.MovieDateDto
import dev.brunofelix.movies.domain.model.MovieDate

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
