package dev.brunofelix.movies.domain.repository

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.WatchProvider
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
     * Replaces the streaming services stored with a favorite media. Does nothing when the
     * media is not a favorite, so it never brings back one the user removed.
     *
     * @param id The ID of the favorite media.
     * @param providers The streaming services that currently offer it.
     * @return A [Resource] that fails when the favorite could not be updated.
     */
    suspend fun updateWatchProviders(id: Long, providers: List<WatchProvider>): Resource<Unit>

    /**
     * Retrieves a flow of all favorite medias.
     *
     * @return A flow emitting a list of all favorite medias.
     * @see Media
     */
    fun getFavoriteMedias(): Flow<List<Media>>
}
