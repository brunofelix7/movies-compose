package dev.brunofelix.movies.feature.movie.domain.model

import dev.brunofelix.movies.core.data.local.entity.MovieEntity

data class Movie(
    val id: Long = -1L,
    val title: String = "",
    val imageUrl: String = "",
    val voteAverage: Float = 0F,
    val details: MovieDetails? = null
) {
    fun toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = id,
            title = title,
            imageUrl = imageUrl,
            voteAverage = voteAverage,
            duration = details?.duration ?: -1
        )
    }
}