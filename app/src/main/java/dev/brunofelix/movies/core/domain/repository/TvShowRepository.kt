package dev.brunofelix.movies.core.domain.repository

import dev.brunofelix.movies.core.domain.model.Cast
import dev.brunofelix.movies.core.domain.model.Episode
import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.model.Video
import dev.brunofelix.movies.core.domain.util.Resource

/**
 * Repository interface for managing TV Show data from remote sources.
 */
interface TvShowRepository {
    /**
     * Fetches a list of popular TV shows for a specific page.
     * @param page The page number to fetch.
     * @return A [Resource] containing a list of [TvShow]s.
     */
    suspend fun getPopularTvShows(page: Int): Resource<List<TvShow>>

    /**
     * Fetches a list of top-rated TV shows for a specific page.
     * @param page The page number to fetch.
     * @return A [Resource] containing a list of [TvShow]s.
     */
    suspend fun getTopRatedTvShows(page: Int): Resource<List<TvShow>>

    /**
     * Fetches detailed information for a specific TV show from the remote source.
     * @param id The unique identifier of the TV show.
     * @return A [Resource] containing the [TvShow] details or an error.
     */
    suspend fun getDetails(id: Long): Resource<TvShow>

    /**
     * Fetches videos for a specific TV show from the remote source.
     * @param id The unique identifier of the TV show.
     * @return A [Resource] containing a list of [Video]s or an error.
     */
    suspend fun getVideos(id: Long): Resource<List<Video>>

    /**
     * Fetches the cast of a specific TV show from the remote source.
     * @param id The unique identifier of the TV show.
     * @return A [Resource] containing a list of [Cast] members or an error.
     */
    suspend fun getCast(id: Long): Resource<List<Cast>>

    /**
     * Fetches the episodes of a single season.
     * @param id The unique identifier of the TV show.
     * @param seasonNumber The position of the season, `0` for specials.
     * @return A [Resource] containing a list of [Episode]s or an error.
     */
    suspend fun getSeasonEpisodes(id: Long, seasonNumber: Int): Resource<List<Episode>>

    /**
     * Fetches the TV shows premiering in [month].
     * @return A [Resource] containing a list of [TvShow]s.
     */
    suspend fun getReleases(month: ReleaseMonth, page: Int): Resource<List<TvShow>>
}