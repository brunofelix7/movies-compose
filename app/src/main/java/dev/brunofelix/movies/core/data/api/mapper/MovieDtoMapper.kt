package dev.brunofelix.movies.core.data.api.mapper

import dev.brunofelix.movies.core.data.api.dto.MovieDto
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.util.extension.toBackdropUrl
import dev.brunofelix.movies.core.util.extension.toPostUrl

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id ?: -1L,
        title = title ?: "Undefined",
        posterPath = posterPath?.toPostUrl() ?: "",
        voteAverage = voteAverage ?: -1.0F,
        genres = genres?.map { it.toDomain() } ?: emptyList(),
        overview = overview ?: "",
        backdropPath = backdropPath?.toBackdropUrl() ?: "",
        releaseDate = releaseDate ?: "",
        duration = runtime ?: 0,
        voteCount = voteCount ?: 0
    )
}