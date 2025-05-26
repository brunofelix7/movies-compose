package dev.brunofelix.movies.core.data.remote.dto

import com.google.gson.annotations.SerializedName
import dev.brunofelix.movies.core.util.extension.toBackdropUrl
import dev.brunofelix.movies.core.util.extension.toPostUrl
import dev.brunofelix.movies.feature.movie.domain.model.Movie
import dev.brunofelix.movies.feature.movie.domain.model.MovieDetails

data class ResultDto(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("adult")
    val adult: Boolean? = null,

    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,

    @SerializedName("popularity")
    val popularity: Double? = null,

    @SerializedName("video")
    val video: Boolean? = null,

    @SerializedName("vote_average")
    val voteAverage: Float? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null,

    @SerializedName("budget")
    val budget: Int? = null,

    @SerializedName("genres")
    val genres: List<GenreDto>? = null,

    @SerializedName("homepage")
    val homepage: String? = null,

    @SerializedName("imdb_id")
    val imdbId: String? = null,

    @SerializedName("origin_country")
    val originCountry: List<String>? = null,

    @SerializedName("revenue")
    val revenue: Long? = null,

    @SerializedName("runtime")
    val runtime: Int? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("tagline")
    val tagline: String? = null
) {
    fun toMovie(): Movie {
        return Movie(
            id = id ?: -1L,
            title = title ?: "Undefined",
            imageUrl = posterPath?.toPostUrl() ?: "",
            voteAverage = voteAverage ?: -1.0F,
            details = MovieDetails(
                genres = genres?.map { it.toGenre() },
                overview = overview ?: "",
                backdropPath = backdropPath?.toBackdropUrl() ?: "",
                releaseDate = releaseDate ?: "",
                duration = runtime ?: 0,
                voteCount = voteCount ?: 0
            )
        )
    }
}