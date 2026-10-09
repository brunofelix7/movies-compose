package dev.brunofelix.movies.data.remote.dto.tv_show

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing a single episode of a TV show season.
 *
 * @property id Unique identifier for the episode.
 * @property name The title of the episode.
 * @property overview A brief summary of the episode.
 * @property stillPath Relative path to the episode's still image.
 * @property episodeNumber The position of the episode within its season.
 * @property seasonNumber The season the episode belongs to.
 * @property runtime The episode duration in minutes.
 * @property airDate The date the episode aired.
 * @property voteAverage The average rating given to the episode.
 */
@Serializable
data class EpisodeDto(
    @SerialName("id")
    val id: Long? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("still_path")
    val stillPath: String? = null,

    @SerialName("episode_number")
    val episodeNumber: Int? = null,

    @SerialName("season_number")
    val seasonNumber: Int? = null,

    @SerialName("runtime")
    val runtime: Int? = null,

    @SerialName("air_date")
    val airDate: String? = null,

    @SerialName("vote_average")
    val voteAverage: Float? = null
)
