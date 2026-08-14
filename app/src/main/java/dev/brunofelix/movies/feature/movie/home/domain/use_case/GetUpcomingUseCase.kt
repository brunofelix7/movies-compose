package dev.brunofelix.movies.feature.movie.home.domain.use_case

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface GetUpcomingUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}

class GetUpcomingUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetUpcomingUseCase {
    override fun invoke(): Flow<PagingData<Movie>> {
        return repository.getUpcomingMovies(PagingConfig(pageSize = 20))
    }
}
