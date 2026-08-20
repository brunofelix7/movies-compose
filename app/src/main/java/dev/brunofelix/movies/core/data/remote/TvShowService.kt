package dev.brunofelix.movies.core.data.remote

import dev.brunofelix.movies.core.data.remote.dto.VideoRootDto
import dev.brunofelix.movies.core.data.remote.dto.tv_show.TvShowDto
import dev.brunofelix.movies.core.data.remote.dto.tv_show.TvShowRootDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit service interface for TMDB TV Show API endpoints.
 */
interface TvShowService {

    /**
     * Fetches a paginated list of popular TV shows.
     * @param page The page number to fetch.
     * @return A [Response] containing a [TvShowRootDto].
     */
    @GET("tv/popular")
    suspend fun getPopulars(
        @Query("page") page: Int
    ): Response<TvShowRootDto>

    /**
     * Fetches a paginated list of top-rated TV shows.
     * @param page The page number to fetch.
     * @return A [Response] containing a [TvShowRootDto].
     */
    @GET("tv/top_rated")
    suspend fun getTopRated(
        @Query("page") page: Int
    ): Response<TvShowRootDto>

    /**
     * Fetches detailed information for a specific TV show.
     * @param id The unique identifier of the TV show.
     * @return A [Response] containing a [TvShowDto].
     */
    @GET("tv/{id}")
    suspend fun getDetails(
        @Path("id") id: Long
    ): Response<TvShowDto>

    /**
     * Searches for TV shows by a query string.
     * @param query The search query.
     * @param page The page number to fetch.
     * @return A [Response] containing a [TvShowRootDto].
     */
    @GET("search/tv")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<TvShowRootDto>

    /**
     * Fetches videos (trailers, teasers, etc.) for a specific TV show.
     * @param id The unique identifier of the TV show.
     * @return A [Response] containing a [VideoRootDto].
     */
    @GET("tv/{id}/videos")
    suspend fun getVideos(
        @Path("id") id: Long
    ): Response<VideoRootDto>
}
