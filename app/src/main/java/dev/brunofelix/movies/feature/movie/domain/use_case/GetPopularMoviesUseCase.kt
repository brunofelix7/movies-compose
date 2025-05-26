package dev.brunofelix.movies.feature.movie.domain.use_case

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.util.exception.RemoteException
import dev.brunofelix.movies.feature.movie.domain.model.Movie
import dev.brunofelix.movies.feature.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface GetPopularMoviesUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}

class GetPopularMoviesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetPopularMoviesUseCase {

    override operator fun invoke(): Flow<PagingData<Movie>> {
        return try {
            repository.fetchPopulars(
                pagingConfig = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        } catch (e: Exception) {
            throw RemoteException(R.string.popular_movies_error, e)
        }
    }
}