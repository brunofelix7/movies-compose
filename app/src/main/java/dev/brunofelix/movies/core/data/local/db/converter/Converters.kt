package dev.brunofelix.movies.core.data.local.db.converter

import androidx.room.TypeConverter
import dev.brunofelix.movies.core.domain.model.enums.MediaType

/**
 * Type converters for Room to handle custom data types.
 */
class Converters {
    /**
     * Converts a [MediaType] to a [String].
     *
     * @param mediaType The media type to convert.
     * @return The string representation of the media type.
     */
    @TypeConverter
    fun fromMediaType(mediaType: MediaType): String {
        return mediaType.name
    }

    /**
     * Converts a [String] to a [MediaType].
     *
     * @param value The string to convert.
     * @return The [MediaType] corresponding to the string.
     */
    @TypeConverter
    fun toMediaType(value: String): MediaType {
        return MediaType.valueOf(value)
    }
}
