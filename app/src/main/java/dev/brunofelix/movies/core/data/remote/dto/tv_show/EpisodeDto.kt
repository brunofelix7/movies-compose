package dev.brunofelix.movies.core.data.remote.dto.tv_show

import com.google.gson.annotations.SerializedName

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
data class EpisodeDto(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("still_path")
    val stillPath: String?,

    @SerializedName("episode_number")
    val episodeNumber: Int?,

    @SerializedName("season_number")
    val seasonNumber: Int?,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("air_date")
    val airDate: String?,

    @SerializedName("vote_average")
    val voteAverage: Float?
)
