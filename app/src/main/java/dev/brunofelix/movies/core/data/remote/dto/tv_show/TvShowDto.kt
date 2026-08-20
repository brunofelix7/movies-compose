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
    val id: Long?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("original_name")
    val originalName: String?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("first_air_date")
    val firstAirDate: String?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("vote_average")
    val voteAverage: Float?,

    @SerializedName("vote_count")
    val voteCount: Int?,

    @SerializedName("genres")
    val genres: List<MovieGenreDto>?,

    @SerializedName("homepage")
    val homepage: String?,

    @SerializedName("origin_country")
    val originCountry: List<String>?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("tagline")
    val tagline: String?,

    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int?,

    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int?,

    @SerializedName("type")
    val type: String?
)
