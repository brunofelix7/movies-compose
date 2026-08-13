package dev.brunofelix.movies.core.data.remote

import dev.brunofelix.movies.core.data.remote.dto.movie.MovieDto
import dev.brunofelix.movies.core.data.remote.dto.movie.MovieRootDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit service interface for TMDB Movie API endpoints.
 */
interface MovieService {

    /**
     * Fetches a paginated list of popular movies.
     * @param page The page number to fetch.
     * @return A [Response] containing a [MovieRootDto].
     */
    @GET("movie/popular")
    suspend fun getPopulars(
        @Query("page") page: Int
    ): Response<MovieRootDto>

    /**
     * Fetches a paginated list of upcoming movies.
     * @param page The page number to fetch.
     * @return A [Response] containing a [MovieRootDto].
     */
    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("page") page: Int
    ): Response<MovieRootDto>

    /**
     * Fetches a paginated list of top-rated movies.
     * @param page The page number to fetch.
     * @return A [Response] containing a [MovieRootDto].
     */
    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("page") page: Int
    ): Response<MovieRootDto>

    /**
     * Fetches detailed information for a specific movie.
     * @param id The unique identifier of the movie.
     * @return A [Response] containing a [MovieDto].
     */
    @GET("movie/{id}")
    suspend fun getDetails(
        @Path("id") id: Long
    ): Response<MovieDto>

    /**
     * Searches for movies by a query string.
     * @param query The search query.
     * @param page The page number to fetch.
     * @return A [Response] containing a [MovieRootDto].
     */
    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MovieRootDto>
}
