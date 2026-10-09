package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.use_case.GetTopRatedMoviesUseCase
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetTopRatedMoviesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetTopRatedMoviesUseCase {

    override suspend operator fun invoke(page: Int): Resource<List<Movie>> {
        return repository.getTopRatedMovies(page)
    }
}
