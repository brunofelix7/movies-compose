package dev.brunofelix.movies.core.data.remote.dto.tv_show

import com.google.gson.annotations.SerializedName
import dev.brunofelix.movies.core.data.remote.dto.movie.MovieGenreDto

/**
 * Data Transfer Object representing a TV show from the TMDB API.
 *
 * @property id Unique identifier for the TV show.
 * @property name The name of the TV show.
 * @property originalName The original name of the TV show in its original language.
 * @property originalLanguage The original language of the TV show.
 * @property overview A brief summary of the TV show's plot.
 * @property posterPath URL or relative path to the TV show's poster image.
 * @property backdropPath URL or relative path to the TV show's backdrop image.
 * @property firstAirDate The date when the TV show first aired.
 * @property genreIds List of genre IDs associated with the TV show.
 * @property popularity Popularity score of the TV show.
 * @property voteAverage The average rating given to the TV show.
 * @property voteCount The total number of votes received by the TV show.
 * @property genres List of [MovieGenreDto] objects representing the genres of the TV show.
 * @property homepage URL to the TV show's official homepage.
 * @property originCountry List of countries where the TV show originated.
 * @property status The production status of the TV show (e.g., Returning Series, Ended).
 * @property tagline A short catchphrase or slogan for the TV show.
 * @property numberOfEpisodes The total number of episodes available for the TV show.
 * @property numberOfSeasons The total number of seasons available for the TV show.
 * @property type The type of the TV show (e.g., Scripted, Reality).
 */
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
