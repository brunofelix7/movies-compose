package dev.brunofelix.movies.core.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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