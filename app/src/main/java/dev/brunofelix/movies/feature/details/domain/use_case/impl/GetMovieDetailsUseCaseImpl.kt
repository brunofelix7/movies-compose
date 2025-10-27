package dev.brunofelix.movies.feature.details.domain.use_case.impl

import dev.brunofelix.movies.core.data.util.Resource
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.feature.details.domain.use_case.GetMovieDetailsUseCase
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieDetailsUseCase {

    override suspend operator fun invoke(id: Long): Resource<Movie> {
        return repository.getDetails(id)
    }
}