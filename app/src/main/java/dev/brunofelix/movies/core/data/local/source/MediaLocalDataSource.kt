package dev.brunofelix.movies.core.data.local.source

import dev.brunofelix.movies.core.domain.model.Media
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the contract for local media data operations.
 * It abstracts the data source implementation from the repository layer.
 */
interface MediaLocalDataSource {
    /**
     * Inserts a media into the local database.
     * @param media The [media] object to be inserted.
     * @return The row ID of the newly inserted media.
     */
    suspend fun insert(media: Media): Long

    /**
     * Deletes a media from the local database.
     * @param media The [media] object to be deleted.
     * @return The number of rows affected by the deletion.
     */
    suspend fun delete(media: Media): Int

    /**
     * Retrieves a specific media by its unique identifier.
     * @param id The unique ID of the media.
     * @return The [Media] if found, or null otherwise.
     */
    suspend fun getById(id: Long): Media?

    /**
     * Retrieves all medias stored in the local database as a reactive stream.
     * @return A [Flow] emitting a list of all [Media]s.
     */
    fun getAll(): Flow<List<Media>>
}
