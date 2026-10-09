package dev.brunofelix.movies.domain.use_case

fun interface SyncFavoriteWatchProvidersUseCase {
    suspend operator fun invoke()
}
