package dev.brunofelix.movies.test_util.fake

import dev.brunofelix.movies.core.data.db.entity.MovieEntity
import dev.brunofelix.movies.core.data.source.MovieLocalDataSource
import kotlinx.coroutines.flow.Flow

class FakeMovieLocalDataSource : MovieLocalDataSource {

    private var shouldReturnError = false

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun insert(entity: MovieEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun delete(entity: MovieEntity): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Long): MovieEntity? {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<MovieEntity>?> {
        TODO("Not yet implemented")
    }
}