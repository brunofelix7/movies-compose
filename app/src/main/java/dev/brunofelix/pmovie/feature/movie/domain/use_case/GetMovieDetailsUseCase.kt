package dev.brunofelix.pmovie.feature.movie.domain.use_case

import dev.brunofelix.pmovie.R
import dev.brunofelix.pmovie.core.util.exception.RemoteException
import dev.brunofelix.pmovie.feature.movie.domain.model.Movie
import dev.brunofelix.pmovie.feature.movie.domain.repository.MovieRepository
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