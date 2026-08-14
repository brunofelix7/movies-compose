package dev.brunofelix.movies.feature.movie.detail.domain.use_case

import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

fun interface GetMovieDetailsUseCase {
    suspend operator fun invoke(id: Long): Resource<Movie>
}

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieDetailsUseCase {

    override suspend operator fun invoke(id: Long): Resource<Movie> {
        return repository.getDetails(id)
    }
}