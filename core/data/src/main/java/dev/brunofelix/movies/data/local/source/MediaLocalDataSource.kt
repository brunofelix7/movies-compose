package dev.brunofelix.movies.data.local.source

import dev.brunofelix.movies.domain.model.Media
import kotlinx.coroutines.flow.Flow

/**
 * Local data source for the medias the user marked as favorite.
 *
 * One-shot operations return a [Result] that fails with a
 * [dev.brunofelix.movies.domain.util.exception.LocalException].
 */
interface MediaLocalDataSource {
    suspend fun insert(media: Media): Result<Unit>

    suspend fun delete(media: Media): Result<Unit>

    suspend fun getById(id: Long): Result<Media?>

    fun getAll(): Flow<List<Media>>
}
