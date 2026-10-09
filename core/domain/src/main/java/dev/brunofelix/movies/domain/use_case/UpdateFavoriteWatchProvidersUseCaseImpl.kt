package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class UpdateFavoriteWatchProvidersUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : UpdateFavoriteWatchProvidersUseCase {

    override suspend operator fun invoke(id: Long, providers: List<WatchProvider>): Resource<Unit> {
        return repository.updateWatchProviders(id, providers)
    }
}
