package dev.brunofelix.pmovie.feature.movie.domain.use_case

import dev.brunofelix.pmovie.R
import dev.brunofelix.pmovie.core.util.exception.LocalException
import dev.brunofelix.pmovie.feature.movie.domain.repository.MovieRepository
import javax.inject.Inject

fun interface IsFavoriteMovieUseCase {
    suspend operator fun invoke(id: Long): Boolean
}

class IsFavoriteMovieUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : IsFavoriteMovieUseCase {

    override suspend operator fun invoke(id: Long): Boolean {
        return try {
            repository.isFavorite(id)
        } catch (e: Exception) {
            throw LocalException(R.string.is_favorite_movie_error, e)
        }
    }
}