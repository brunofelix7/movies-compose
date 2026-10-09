package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.WatchProvider
import dev.brunofelix.movies.domain.util.Resource

fun interface GetTvShowWatchProvidersUseCase {
    suspend operator fun invoke(id: Long): Resource<List<WatchProvider>>
}
