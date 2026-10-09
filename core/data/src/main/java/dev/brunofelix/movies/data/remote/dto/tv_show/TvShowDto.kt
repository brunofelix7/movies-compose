package dev.brunofelix.movies.data.remote.dto.tv_show

import dev.brunofelix.movies.data.remote.dto.movie.MovieGenreDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
 * @property seasons The seasons of the TV show, without their episodes.
 * @property type The type of the TV show (e.g., Scripted, Reality).
 */
@Serializable
data class TvShowDto(
    @SerialName("id")
    val id: Long? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("original_name")
    val originalName: String? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int>? = null,

    @SerialName("popularity")
    val popularity: Double? = null,

    @SerialName("vote_average")
    val voteAverage: Float? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null,

    @SerialName("genres")
    val genres: List<MovieGenreDto>? = null,

    @SerialName("homepage")
    val homepage: String? = null,

    @SerialName("origin_country")
    val originCountry: List<String>? = null,

    @SerialName("status")
    val status: String? = null,

    @SerialName("tagline")
    val tagline: String? = null,

    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int? = null,

    @SerialName("number_of_seasons")
    val numberOfSeasons: Int? = null,

    @SerialName("seasons")
    val seasons: List<SeasonDto>? = null,

    @SerialName("type")
    val type: String? = null
)
