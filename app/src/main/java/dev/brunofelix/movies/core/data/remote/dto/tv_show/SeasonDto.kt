package dev.brunofelix.movies.core.data.remote.dto.tv_show

import com.google.gson.annotations.SerializedName

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
data class SeasonDto(
    @SerializedName("id")
    val id: Long?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("season_number")
    val seasonNumber: Int?,

    @SerializedName("episode_count")
    val episodeCount: Int?,

    @SerializedName("air_date")
    val airDate: String?,

    @SerializedName("vote_average")
    val voteAverage: Float?,

    @SerializedName("episodes")
    val episodes: List<EpisodeDto>?
)
