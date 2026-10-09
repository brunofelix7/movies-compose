package dev.brunofelix.movies.domain.repository

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.util.Resource
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
     * @return A [Resource] that fails when the media could not be stored.
     */
    suspend fun save(media: Media): Resource<Unit>

    /**
     * Deletes a media from the repository.
     *
     * @param media The media to be deleted.
     * @return A [Resource] that fails when the media could not be removed.
     */
    suspend fun delete(media: Media): Resource<Unit>

    /**
     * Checks if a media with the given ID is marked as a favorite.
     *
     * @param id The ID of the media to check.
     * @return A [Resource] holding `true` if the media is marked as a favorite.
     */
    suspend fun isFavorite(id: Long): Resource<Boolean>

    /**
     * Retrieves a flow of all favorite medias.
     *
     * @return A flow emitting a list of all favorite medias.
     * @see Media
     */
    fun getFavoriteMedias(): Flow<List<Media>>
}
