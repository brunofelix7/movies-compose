package dev.brunofelix.movies.core.data.api

import dev.brunofelix.movies.core.data.api.dto.MovieDto
import dev.brunofelix.movies.core.data.api.dto.MovieRootDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopulars(
        @Query("page") page: Int
    ): Response<MovieRootDto>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("page") page: Int
    ): Response<MovieRootDto>

    @GET("movie/{id}")
    suspend fun getDetails(
        @Path("id") id: Long
    ): Response<MovieDto>

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MovieRootDto>
}