package dev.brunofelix.movies.core.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database entity representing a movie in the "movies" table.
 *
 * @property id Unique identifier for the movie (matches the remote API ID).
 * @property title The title of the movie.
 * @property posterPath URL or relative path to the movie's poster image.
 * @property voteAverage The average rating given to the movie.
 * @property duration The duration of the movie in minutes.
 * @property releaseDate The date when the movie was released.
 */
@Entity(tableName = "movies")
data class MovieEntity(
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
)
