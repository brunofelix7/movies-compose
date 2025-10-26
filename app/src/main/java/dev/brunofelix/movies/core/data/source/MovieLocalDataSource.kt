package dev.brunofelix.movies.core.data.source

import dev.brunofelix.movies.core.data.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    suspend fun insert(entity: MovieEntity): Long

    suspend fun delete(entity: MovieEntity): Int

    suspend fun getById(id: Long): MovieEntity?

    fun getAll(): Flow<List<MovieEntity>>
}