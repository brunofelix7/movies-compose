package dev.brunofelix.movies.data.repository

import dev.brunofelix.movies.data.local.source.MediaLocalDataSource
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.toResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of [MediaRepository].
 * @property localDataSource The source for local media data.
 */
class MediaRepositoryImpl @Inject constructor(
    private val localDataSource: MediaLocalDataSource
) : MediaRepository {

    override suspend fun save(media: Media): Resource<Unit> {
        return localDataSource.insert(media).toResource()
    }

    override suspend fun delete(media: Media): Resource<Unit> {
        return localDataSource.delete(media).toResource()
    }

    override suspend fun isFavorite(id: Long): Resource<Boolean> {
        return localDataSource.getById(id).map { it != null }.toResource()
    }

    override fun getFavoriteMedias(): Flow<List<Media>> = localDataSource.getAll()
}
