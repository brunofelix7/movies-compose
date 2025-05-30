package dev.brunofelix.movies.feature.upcoming.domain.use_case

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.util.exception.RemoteException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface GetUpcomingUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}

class GetUpcomingUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetUpcomingUseCase {

    override operator fun invoke(): Flow<PagingData<Movie>> {
        return try {
            repository.fetchUpcoming(
                pagingConfig = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        } catch (e: Exception) {
            throw RemoteException(R.string.upcoming_movies_error, e)
        }
    }
}