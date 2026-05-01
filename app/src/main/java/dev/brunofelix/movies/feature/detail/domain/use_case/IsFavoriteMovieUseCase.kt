package dev.brunofelix.movies.feature.detail.domain.use_case

fun interface IsFavoriteMovieUseCase {
    suspend operator fun invoke(id: Long): Boolean
}