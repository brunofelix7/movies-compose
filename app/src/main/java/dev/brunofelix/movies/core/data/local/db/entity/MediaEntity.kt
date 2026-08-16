package dev.brunofelix.movies.core.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.brunofelix.movies.core.domain.model.enums.MediaType

/**
 * Represents a media entity in the database.
 *
 * @property id The unique identifier of the media.
 * @property title The title of the media.
 * @property posterPath The URL of the media's poster image.
 * @property voteAverage The average rating of the media.
 * @property duration The duration of the media.
 * @property releaseDate The release date of the media.
 * @property type The type of the media.
 */
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
    val type: MediaType
)