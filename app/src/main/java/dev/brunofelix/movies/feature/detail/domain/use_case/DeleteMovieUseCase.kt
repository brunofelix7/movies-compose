package dev.brunofelix.movies.feature.detail.domain.use_case

import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.util.exception.LocalException
import javax.inject.Inject

fun interface DeleteMovieUseCase {
    suspend operator fun invoke(movie: Movie)
}

class DeleteMovieUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : DeleteMovieUseCase {

    override suspend fun invoke(movie: Movie) {
        try {
            repository.delete(movie)
        } catch (e: Exception) {
            throw LocalException(R.string.delete_movie_error, e)
        }
    }
}