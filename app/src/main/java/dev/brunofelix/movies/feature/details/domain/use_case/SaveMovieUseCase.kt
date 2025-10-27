package dev.brunofelix.movies.feature.details.domain.use_case

import dev.brunofelix.movies.core.domain.model.Movie

fun interface SaveMovieUseCase {
    suspend operator fun invoke(movie: Movie)
}