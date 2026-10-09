package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.use_case.GetUpcomingMoviesUseCase
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetUpcomingMoviesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetUpcomingMoviesUseCase {

    override suspend operator fun invoke(page: Int): Resource<List<Movie>> {
        return repository.getUpcomingMovies(page)
    }
}
