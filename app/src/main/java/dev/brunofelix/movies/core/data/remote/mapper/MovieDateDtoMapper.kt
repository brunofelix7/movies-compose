package dev.brunofelix.movies.core.data.remote.mapper

import dev.brunofelix.movies.core.data.remote.dto.MovieDateDto
import dev.brunofelix.movies.core.domain.model.MovieDate

fun MovieDateDto.toDomain(): MovieDate {
    return MovieDate(
        maximum = maximum.orEmpty(),
        minimum = minimum.orEmpty()
    )
}
