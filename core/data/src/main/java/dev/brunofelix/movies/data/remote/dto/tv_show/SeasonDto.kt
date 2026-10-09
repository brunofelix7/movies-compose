package dev.brunofelix.movies.data.remote.dto.tv_show

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing a season of a TV show from the TMDB API.
 *
 * The same shape is returned inside the `seasons` array of `tv/{id}` and as the body of
 * `tv/{id}/season/{season_number}`, where [episodes] is also present.
 *
 * @property id Unique identifier for the season.
 * @property name The name of the season.
 * @property overview A brief summary of the season.
 * @property posterPath Relative path to the season's poster image.
 * @property seasonNumber The position of the season, `0` for specials.
 * @property episodeCount The number of episodes in the season.
 * @property airDate The date the season started airing.
 * @property voteAverage The average rating given to the season.
 * @property episodes The episodes of the season, only present on the season endpoint.
 */
@Serializable
data class SeasonDto(
    @SerialName("id")
    val id: Long? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("season_number")
    val seasonNumber: Int? = null,

    @SerialName("episode_count")
    val episodeCount: Int? = null,

    @SerialName("air_date")
    val airDate: String? = null,

    @SerialName("vote_average")
    val voteAverage: Float? = null,

    @SerialName("episodes")
    val episodes: List<EpisodeDto>? = null
)
