package dev.brunofelix.movies.feature.detail.domain.use_case

import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.util.exception.LocalException
import javax.inject.Inject

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