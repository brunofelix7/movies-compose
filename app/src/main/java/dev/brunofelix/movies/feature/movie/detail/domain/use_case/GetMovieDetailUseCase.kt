package dev.brunofelix.movies.feature.movie.detail.domain.use_case

import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.util.Resource
import javax.inject.Inject

fun interface GetMovieDetailUseCase {
    suspend operator fun invoke(id: Long): Resource<Movie>
}

class GetMovieDetailUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieDetailUseCase {

    override suspend operator fun invoke(id: Long): Resource<Movie> {
        return repository.getDetails(id)
    }
}