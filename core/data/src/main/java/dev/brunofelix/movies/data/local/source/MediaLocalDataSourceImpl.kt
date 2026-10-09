package dev.brunofelix.movies.data.local.source

import dev.brunofelix.movies.data.local.dao.MediaDao
import dev.brunofelix.movies.data.local.mapper.toDomain
import dev.brunofelix.movies.data.local.mapper.toEntity
import dev.brunofelix.movies.data.util.safeLocalCall
import dev.brunofelix.movies.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaLocalDataSourceImpl @Inject constructor(
    private val dao: MediaDao
) : MediaLocalDataSource {

    override suspend fun insert(media: Media): Result<Unit> = safeLocalCall {
        dao.insert(media.toEntity())
        Unit
    }

    override suspend fun delete(media: Media): Result<Unit> = safeLocalCall {
        dao.delete(media.toEntity())
        Unit
    }

    override suspend fun getById(id: Long): Result<Media?> = safeLocalCall {
        dao.getById(id)?.toDomain()
    }

    override fun getAll(): Flow<List<Media>> {
        return dao.getAll().map { entityList ->
            entityList.map { it.toDomain() }
        }
    }
}
