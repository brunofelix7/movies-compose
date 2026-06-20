package dev.brunofelix.movies.test_util.fake

import dev.brunofelix.movies.core.data.source.MovieLocalDataSource
import dev.brunofelix.movies.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

class FakeMovieLocalDataSource : MovieLocalDataSource {

    private var shouldReturnError = false

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun insert(movie: Movie): Long {
        TODO("Not yet implemented")
    }

    override suspend fun delete(movie: Movie): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Long): Movie? {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }
}