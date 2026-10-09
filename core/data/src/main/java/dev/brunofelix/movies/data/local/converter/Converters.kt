package dev.brunofelix.movies.data.local.converter

import androidx.room.TypeConverter
import dev.brunofelix.movies.data.local.entity.WatchProviderEntity
import dev.brunofelix.movies.domain.model.enums.MediaType
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }
private val watchProvidersSerializer = ListSerializer(WatchProviderEntity.serializer())

class Converters {
    @TypeConverter
    fun fromMediaType(mediaType: MediaType): String {
        return mediaType.name
    }

    @TypeConverter
    fun toMediaType(value: String): MediaType {
        return MediaType.valueOf(value)
    }

    @TypeConverter
    fun fromWatchProviders(providers: List<WatchProviderEntity>?): String? {
        return providers?.let { json.encodeToString(watchProvidersSerializer, it) }
    }

    /**
     * A value that can't be decoded reads as `null`, so the providers are fetched again
     * instead of the whole favorite failing to load.
     */
    @TypeConverter
    fun toWatchProviders(value: String?): List<WatchProviderEntity>? {
        return value?.let { runCatching { json.decodeFromString(watchProvidersSerializer, it) }.getOrNull() }
    }
}
