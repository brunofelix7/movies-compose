package dev.brunofelix.movies.core.data.remote.dto.tv_show

import com.google.gson.annotations.SerializedName
import dev.brunofelix.movies.core.data.remote.dto.movie.MovieGenreDto

data class TvShowDto(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("original_name")
    val originalName: String? = null,

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,

    @SerializedName("popularity")
    val popularity: Double? = null,

    @SerializedName("vote_average")
    val voteAverage: Float? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null,

    @SerializedName("genres")
    val genres: List<MovieGenreDto>? = null,

    @SerializedName("homepage")
    val homepage: String? = null,

    @SerializedName("origin_country")
    val originCountry: List<String>? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("tagline")
    val tagline: String? = null,

    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int? = null,

    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int? = null,

    @SerializedName("type")
    val type: String? = null
)
