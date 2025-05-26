package dev.brunofelix.movies.feature.movie.domain.use_case

import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.util.exception.LocalException
import dev.brunofelix.movies.feature.movie.domain.model.Movie
import dev.brunofelix.movies.feature.movie.domain.repository.MovieRepository
import javax.inject.Inject

fun interface MarkAsFavoriteUseCase {
    suspend operator fun invoke(movie: Movie)
}

class MarkAsFavoriteUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : MarkAsFavoriteUseCase {

    override suspend operator fun invoke(movie: Movie) {
        try {
            repository.markAsFavorite(movie)
        } catch (e: Exception) {
            throw LocalException(R.string.mark_favorite_error, e)
        }
    }
}