package dev.brunofelix.movies.feature.movie.home.domain.use_case

import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.util.Resource
import javax.inject.Inject

fun interface GetTopRatedUseCase {
    suspend operator fun invoke(page: Int): Resource<List<Movie>>
}

class GetTopRatedUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetTopRatedUseCase {
    override suspend fun invoke(page: Int): Resource<List<Movie>> {
        return repository.getTopRatedMovies(page)
    }
}
