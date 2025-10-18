package dev.brunofelix.movies.core.domain.model

import dev.brunofelix.movies.core.data.db.entity.MovieEntity

data class Movie(
    val id: Long = -1L,
    val title: String = "",
    val imageUrl: String = "",
    val voteAverage: Float = 0F,
    val details: MovieDetails? = null,
    private var isVoteAverageVisible: Boolean = true
) {
    fun toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = id,
            title = title,
            imageUrl = imageUrl,
            voteAverage = voteAverage,
            duration = details?.duration ?: 0,
            releaseDate = details?.releaseDate ?: ""
        )
    }

    fun isVoteAverageVisible() = isVoteAverageVisible

    fun setVoteAverageVisibility(isVisible: Boolean) {
        isVoteAverageVisible = isVisible
    }
}