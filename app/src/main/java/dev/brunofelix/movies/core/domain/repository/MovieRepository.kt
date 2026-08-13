package dev.brunofelix.movies.core.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.data.util.Resource
import dev.brunofelix.movies.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun save(movie: Movie)
    suspend fun delete(movie: Movie)
    suspend fun isFavorite(id: Long): Boolean
    suspend fun getDetails(id: Long): Resource<Movie>
    fun getPopularMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>>
    fun getUpcomingMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>>
    fun getTopRatedMovies(pagingConfig: PagingConfig): Flow<PagingData<Movie>>
    fun getFavoriteMovies(): Flow<List<Movie>>
}