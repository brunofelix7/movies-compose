package dev.brunofelix.movies.core.data.api.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
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
    val genres: List<MovieGenreDto>? = null,

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
)
