package dev.brunofelix.movies.core.data.remote.source

import dev.brunofelix.movies.core.domain.model.Cast
import dev.brunofelix.movies.core.domain.model.Episode
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.model.Video

/**
 * Remote data source for TV Show-related operations.
 */
interface TvShowRemoteDataSource {
    /**
     * Fetches a list of popular TV shows for a specific page.
     */
    suspend fun getPopulars(page: Int): Result<List<TvShow>>

    /**
     * Fetches a list of top-rated TV shows for a specific page.
     */
    suspend fun getTopRated(page: Int): Result<List<TvShow>>

    /**
     * Searches for TV shows by a query string.
     * @param query The search query.
     * @param page The page number to fetch.
     * @return A [Result] containing a list of [TvShow]s.
     */
    suspend fun search(query: String, page: Int): Result<List<TvShow>>

    /**
     * Fetches TV show details by ID.
     * @param id The unique TV show identifier.
     * @return A [Result] containing the [TvShow] domain model.
     */
    suspend fun getDetails(id: Long): Result<TvShow>

    /**
     * Fetches TV show videos by ID.
     * @param id The unique TV show identifier.
     * @return A [Result] containing a list of [Video] domain models.
     */
    suspend fun getVideos(id: Long): Result<List<Video>>

    /**
     * Fetches the TV show cast by ID.
     * @param id The unique TV show identifier.
     * @return A [Result] containing a list of [Cast] domain models.
     */
    suspend fun getCast(id: Long): Result<List<Cast>>

    /**
     * Fetches the episodes of a single season.
     * @param id The unique TV show identifier.
     * @param seasonNumber The position of the season, `0` for specials.
     * @return A [Result] containing a list of [Episode] domain models.
     */
    suspend fun getSeasonEpisodes(id: Long, seasonNumber: Int): Result<List<Episode>>

    /**
     * Fetches TV shows premiering between [startDate] and [endDate].
     * @param startDate Inclusive lower bound, as `yyyy-MM-dd`.
     * @param endDate Inclusive upper bound, as `yyyy-MM-dd`.
     * @return A [Result] containing a list of [TvShow]s.
     */
    suspend fun getReleases(
        startDate: String,
        endDate: String,
        page: Int
    ): Result<List<TvShow>>
}
