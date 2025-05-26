package dev.brunofelix.movies.feature.movie.domain.use_case

import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.util.exception.RemoteException
import dev.brunofelix.movies.feature.movie.domain.model.Movie
import dev.brunofelix.movies.feature.movie.domain.repository.MovieRepository
import javax.inject.Inject

fun interface GetMovieDetailsUseCase {
    suspend operator fun invoke(id: Long): Movie?
}

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieDetailsUseCase {

    override suspend operator fun invoke(id: Long): Movie? {
        return try {
            repository.getDetails(id)
        } catch (e: Exception) {
            throw RemoteException(R.string.movie_details_error, e)
        }
    }
}