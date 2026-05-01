package dev.brunofelix.movies.feature.detail.domain.use_case

import dev.brunofelix.movies.core.domain.model.Movie

fun interface DeleteMovieUseCase {
    suspend operator fun invoke(movie: Movie)
}