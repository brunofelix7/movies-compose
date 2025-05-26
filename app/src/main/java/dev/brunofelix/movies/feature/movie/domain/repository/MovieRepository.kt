package dev.brunofelix.movies.feature.movie.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.feature.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchPopulars(pagingConfig: PagingConfig): Flow<PagingData<Movie>>

    fun fetchUpcoming(pagingConfig: PagingConfig): Flow<PagingData<Movie>>

    suspend fun getDetails(id: Long): Movie?

    suspend fun markAsFavorite(movie: Movie)

    suspend fun deleteFromFavorites(movie: Movie)

    suspend fun isFavorite(id: Long): Boolean

    fun fetchFavorites(): Flow<List<Movie>?>
}