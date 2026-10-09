package dev.brunofelix.movies.data.local.entity

import androidx.room.ColumnInfo

/**
 * Partial [MediaEntity] that only replaces the streaming services of a stored media.
 */
data class MediaWatchProvidersUpdate(
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "watchProviders")
    val watchProviders: List<WatchProviderEntity>?
)
