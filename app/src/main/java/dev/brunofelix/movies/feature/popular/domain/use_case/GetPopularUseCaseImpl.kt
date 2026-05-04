package dev.brunofelix.movies.feature.popular.domain.use_case

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.util.exception.RemoteException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetPopularUseCase {

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