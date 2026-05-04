package dev.brunofelix.movies.core.data.source

import dev.brunofelix.movies.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    suspend fun insert(movie: Movie): Long

    suspend fun delete(movie: Movie): Int

    suspend fun getById(id: Long): Movie?

    fun getAll(): Flow<List<Movie>>
}