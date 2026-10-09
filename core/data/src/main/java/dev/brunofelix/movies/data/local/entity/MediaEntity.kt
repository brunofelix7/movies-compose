package dev.brunofelix.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.brunofelix.movies.domain.model.enums.MediaType

@Entity(tableName = "medias")
data class MediaEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "posterPath")
    val posterPath: String,

    @ColumnInfo(name = "voteAverage")
    val voteAverage: Float,

    @ColumnInfo(name = "duration")
    val duration: Int,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,

    @ColumnInfo(name = "type")
    val type: MediaType,

    /** `null` until the streaming services are fetched for the first time. */
    @ColumnInfo(name = "watchProviders")
    val watchProviders: List<WatchProviderEntity>? = null
)
