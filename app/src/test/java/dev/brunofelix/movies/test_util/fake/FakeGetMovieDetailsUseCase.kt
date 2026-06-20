package dev.brunofelix.movies.test_util.fake

import dev.brunofelix.movies.core.data.util.Resource
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.util.exception.RemoteException
import dev.brunofelix.movies.feature.detail.domain.use_case.GetMovieDetailsUseCase

class FakeGetMovieDetailsUseCase (
    private val repository: FakeMovieRepository
): GetMovieDetailsUseCase {

    override suspend fun invoke(id: Long): Resource<Movie> {
        return try {
            repository.getDetails(id)
        } catch (e: Exception) {
            throw RemoteException(0, e)
        }
    }
}