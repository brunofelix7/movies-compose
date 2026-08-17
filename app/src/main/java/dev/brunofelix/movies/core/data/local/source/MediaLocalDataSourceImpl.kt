package dev.brunofelix.movies.core.data.local.source

import dev.brunofelix.movies.core.data.local.db.dao.MediaDao
import dev.brunofelix.movies.core.data.local.db.mapper.toDomain
import dev.brunofelix.movies.core.data.local.db.mapper.toEntity
import dev.brunofelix.movies.core.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of [MediaLocalDataSource] using Room database as the persistence engine.
 * @property dao The [MediaDao] used to perform database operations.
 */
class MediaLocalDataSourceImpl @Inject constructor(
    private val dao: MediaDao
): MediaLocalDataSource {

    override suspend fun insert(media: Media): Long {
        return dao.insert(media.toEntity())
    }

    override suspend fun delete(media: Media): Int {
        return dao.delete(media.toEntity())
    }

    override suspend fun getById(id: Long): Media? {
        return dao.getById(id)?.toDomain()
    }

    override fun getAll(): Flow<List<Media>> {
        return dao.getAll().map { entityList ->
            entityList.map { it.toDomain() }
        }
    }
}