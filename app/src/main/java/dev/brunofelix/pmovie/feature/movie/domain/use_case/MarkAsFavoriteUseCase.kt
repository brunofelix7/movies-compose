package dev.brunofelix.pmovie.feature.movie.domain.use_case

import dev.brunofelix.pmovie.R
import dev.brunofelix.pmovie.core.util.exception.LocalException
import dev.brunofelix.pmovie.feature.movie.domain.model.Movie
import dev.brunofelix.pmovie.feature.movie.domain.repository.MovieRepository
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