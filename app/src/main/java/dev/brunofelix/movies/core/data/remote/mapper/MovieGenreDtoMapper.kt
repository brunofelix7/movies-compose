package dev.brunofelix.movies.core.data.remote.mapper

import dev.brunofelix.movies.core.data.remote.dto.movie.MovieGenreDto
import dev.brunofelix.movies.core.domain.model.MovieGenre

fun MovieGenreDto.toDomain(): MovieGenre {
    return MovieGenre(
        id = id ?: -1,
        name = name ?: "--"
    )
}
