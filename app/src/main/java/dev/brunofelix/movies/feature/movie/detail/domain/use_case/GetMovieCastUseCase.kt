package dev.brunofelix.movies.feature.movie.detail.domain.use_case

import dev.brunofelix.movies.core.domain.model.Cast
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.util.Resource
import javax.inject.Inject

fun interface GetMovieCastUseCase {
    suspend operator fun invoke(id: Long): Resource<List<Cast>>
}

class GetMovieCastUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieCastUseCase {
    override suspend fun invoke(id: Long): Resource<List<Cast>> {
        return repository.getCast(id)
    }
}
