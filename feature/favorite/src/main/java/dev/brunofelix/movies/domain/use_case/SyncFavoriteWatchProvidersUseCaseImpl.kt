package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaType
import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.repository.MovieRepository
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Fetches the streaming services of the favorites that have none stored yet: the ones saved
 * before they were stored, or while they failed to load. It is best effort: a favorite whose
 * request fails stays pending and is retried on the next sync.
 */
class SyncFavoriteWatchProvidersUseCaseImpl @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) : SyncFavoriteWatchProvidersUseCase {

    override suspend operator fun invoke() {
        val pending = mediaRepository.getFavoriteMedias()
            .catch { emit(emptyList()) }
            .first()
            .filter { it.watchProviders == null }

        coroutineScope {
            pending.forEach { media -> launch { sync(media) } }
        }
    }

    private suspend fun sync(media: Media) {
        val result = when (media.type) {
            MediaType.MOVIE -> movieRepository.getWatchProviders(media.id)
            MediaType.TV_SHOW -> tvShowRepository.getWatchProviders(media.id)
        }
        if (result is Resource.Success) {
            mediaRepository.updateWatchProviders(media.id, result.data)
        }
    }
}
