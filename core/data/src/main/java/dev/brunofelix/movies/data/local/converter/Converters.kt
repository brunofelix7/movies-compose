package dev.brunofelix.movies.data.local.converter

import androidx.room.TypeConverter
import dev.brunofelix.movies.domain.model.enums.MediaType

class Converters {
    @TypeConverter
    fun fromMediaType(mediaType: MediaType): String {
        return mediaType.name
    }

    @TypeConverter
    fun toMediaType(value: String): MediaType {
        return MediaType.valueOf(value)
    }
}
