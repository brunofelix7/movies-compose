package dev.brunofelix.movies.core.data.api.mapper

import dev.brunofelix.movies.core.data.api.dto.MovieDto
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.model.MovieDetails
import dev.brunofelix.movies.core.util.extension.toBackdropUrl
import dev.brunofelix.movies.core.util.extension.toPostUrl

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id ?: -1L,
        title = title ?: "Undefined",
        imageUrl = posterPath?.toPostUrl() ?: "",
        voteAverage = voteAverage ?: -1.0F,
        details = MovieDetails(
            genres = genres?.map { it.toDomain() },
            overview = overview ?: "",
            backdropPath = backdropPath?.toBackdropUrl() ?: "",
            releaseDate = releaseDate ?: "",
            duration = runtime ?: 0,
            voteCount = voteCount ?: 0
        )
    )
}