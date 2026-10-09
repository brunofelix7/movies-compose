package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.util.Resource

fun interface UpdateFavoriteWatchProvidersUseCase {
    suspend operator fun invoke(id: Long, providers: List<WatchProvider>): Resource<Unit>
}
