package dev.brunofelix.movies.core.data.repository

import dev.brunofelix.movies.core.data.local.source.MediaLocalDataSource
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of [MediaRepository].
 * @property localDataSource The source for local media data.
 */
class MediaRepositoryImpl @Inject constructor(
    private val localDataSource: MediaLocalDataSource
): MediaRepository {

    override suspend fun save(media: Media) {
        localDataSource.insert(media)
    }

    override suspend fun delete(media: Media) {
        localDataSource.delete(media)
    }

    override suspend fun isFavorite(id: Long): Boolean {
        return localDataSource.getById(id) != null
    }

    override fun getFavoriteMedias(): Flow<List<Media>> {
        return localDataSource.getAll().map { entityList ->
            entityList.map { it }
        }
    }
}