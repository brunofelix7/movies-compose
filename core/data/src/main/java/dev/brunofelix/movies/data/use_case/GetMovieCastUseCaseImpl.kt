package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.use_case.GetMovieCastUseCase
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetMovieCastUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieCastUseCase {

    override suspend operator fun invoke(id: Long): Resource<List<Cast>> {
        return repository.getCast(id)
    }
}
