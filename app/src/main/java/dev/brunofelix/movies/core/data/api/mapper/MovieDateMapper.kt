package dev.brunofelix.movies.core.data.api.mapper

import dev.brunofelix.movies.core.data.api.dto.MovieDateDto
import dev.brunofelix.movies.core.domain.model.MovieDate

fun MovieDateDto.toDomain(): MovieDate {
    return MovieDate(
        maximum = maximum.orEmpty(),
        minimum = minimum.orEmpty()
    )
}