package dev.brunofelix.movies.feature.movie.home.domain.use_case

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface GetTopRatedUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}

class GetTopRatedUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetTopRatedUseCase {
    override fun invoke(): Flow<PagingData<Movie>> {
        return repository.getTopRatedMovies(PagingConfig(pageSize = 20))
    }
}
