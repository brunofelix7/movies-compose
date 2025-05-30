package dev.brunofelix.movies.feature.details.domain.use_case

import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.util.exception.RemoteException
import javax.inject.Inject

fun interface GetDetailsUseCase {
    suspend operator fun invoke(id: Long): Movie?
}

class GetDetailsUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetDetailsUseCase {

    override suspend operator fun invoke(id: Long): Movie? {
        return try {
            repository.getDetails(id)
        } catch (e: Exception) {
            throw RemoteException(R.string.movie_details_error, e)
        }
    }
}