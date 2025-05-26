package dev.brunofelix.movies.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.brunofelix.movies.feature.movie.domain.model.Movie
import dev.brunofelix.movies.feature.movie.domain.model.MovieDetails

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,

    @ColumnInfo(name = "voteAverage")
    val voteAverage: Float,

    @ColumnInfo(name = "duration")
    val duration: Int
) {
    fun toMovie(): Movie {
        return Movie(
            id = id,
            title = title,
            imageUrl = imageUrl,
            voteAverage = voteAverage,
            details = MovieDetails(
                duration = duration
            )
        )
    }
}