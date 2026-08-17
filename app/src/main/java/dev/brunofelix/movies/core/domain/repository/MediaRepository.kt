package dev.brunofelix.movies.core.domain.repository

import dev.brunofelix.movies.core.domain.model.Media
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the contract for media-related repository operations.
 * It abstracts the data source implementation from the domain layer.
 *
 * @see Media
 * @see Flow
 */
interface MediaRepository {
    /**
     * Saves a media to the repository.
     *
     * @param media The media to be saved.
     * @see Media
     */
    suspend fun save(media: Media)

    /**
     * Deletes a media from the repository.
     *
     * @param media The media to be deleted.
     * @see Media
     */
    suspend fun delete(media: Media)

    /**
     * Checks if a media with the given ID is marked as a favorite.
     *
     * @param id The ID of the media to check.
     * @return `true` if the media is marked as a favorite, `false` otherwise.
     * @see Media
     */
    suspend fun isFavorite(id: Long): Boolean

    /**
     * Retrieves a flow of all favorite medias.
     *
     * @return A flow emitting a list of all favorite medias.
     * @see Media
     */
    fun getFavoriteMedias(): Flow<List<Media>>
}