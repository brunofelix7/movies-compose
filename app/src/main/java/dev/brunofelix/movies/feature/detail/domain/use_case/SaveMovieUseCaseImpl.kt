package dev.brunofelix.movies.feature.detail.domain.use_case

import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.util.exception.LocalException
import javax.inject.Inject

class SaveMovieUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : SaveMovieUseCase {

    override suspend operator fun invoke(movie: Movie) {
        try {
            repository.save(movie)
        } catch (e: Exception) {
            throw LocalException(R.string.mark_favorite_error, e)
        }
    }
}