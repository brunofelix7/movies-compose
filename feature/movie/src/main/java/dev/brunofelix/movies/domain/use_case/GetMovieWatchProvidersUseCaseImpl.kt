package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetMovieWatchProvidersUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieWatchProvidersUseCase {

    override suspend operator fun invoke(id: Long): Resource<List<WatchProvider>> {
        return repository.getWatchProviders(id)
    }
}
