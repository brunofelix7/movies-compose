package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetMovieDetailUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieDetailUseCase {

    override suspend operator fun invoke(id: Long): Resource<Movie> {
        return repository.getDetails(id)
    }
}
