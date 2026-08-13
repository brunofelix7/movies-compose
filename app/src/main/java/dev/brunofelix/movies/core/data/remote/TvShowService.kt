package dev.brunofelix.movies.core.data.remote

import dev.brunofelix.movies.core.data.remote.dto.tv_show.TvShowDto
import dev.brunofelix.movies.core.data.remote.dto.tv_show.TvShowRootDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {

    @GET("tv/popular")
    suspend fun getPopulars(
        @Query("page") page: Int
    ): Response<TvShowRootDto>

    @GET("tv/top_rated")
    suspend fun getTopRated(
        @Query("page") page: Int
    ): Response<TvShowRootDto>

    @GET("tv/{id}")
    suspend fun getDetails(
        @Path("id") id: Long
    ): Response<TvShowDto>

    @GET("search/tv")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<TvShowRootDto>
}
