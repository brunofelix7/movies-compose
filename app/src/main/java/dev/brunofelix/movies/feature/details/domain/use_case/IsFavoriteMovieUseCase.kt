package dev.brunofelix.movies.feature.details.domain.use_case

fun interface IsFavoriteMovieUseCase {
    suspend operator fun invoke(id: Long): Boolean
}