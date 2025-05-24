package dev.brunofelix.pmovie.feature.movie.fake

import dev.brunofelix.pmovie.core.util.exception.RemoteException
import dev.brunofelix.pmovie.feature.movie.domain.model.Movie
import dev.brunofelix.pmovie.feature.movie.domain.use_case.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetMovieDetailsUseCase (
    private val repository: FakeMovieRepository
): GetMovieDetailsUseCase {

    override suspend fun invoke(id: Long): Flow<Movie?> = flow {
        try {
            emit(repository.getDetails(id))
        } catch (e: Exception) {
            throw RemoteException()
        }
    }
}