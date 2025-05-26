package dev.brunofelix.movies.core.data.remote

import dev.brunofelix.movies.core.data.remote.dto.MovieDto
import dev.brunofelix.movies.core.data.remote.dto.ResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopulars(
        @Query("page") page: Int
    ): Response<MovieDto>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("page") page: Int
    ): Response<MovieDto>

    @GET("movie/{id}")
    suspend fun getDetails(
        @Path("id") id: Long
    ): Response<ResultDto>

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MovieDto>
}