package dev.brunofelix.movies.core.data.api.mapper

import dev.brunofelix.movies.core.data.api.dto.MovieGenreDto
import dev.brunofelix.movies.core.domain.model.MovieGenre

fun MovieGenreDto.toDomain(): MovieGenre {
    return MovieGenre(
        id = id ?: -1,
        name = name ?: "--"
    )
}