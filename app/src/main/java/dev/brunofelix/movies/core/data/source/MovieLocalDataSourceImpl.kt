package dev.brunofelix.movies.core.data.source

import dev.brunofelix.movies.core.data.db.dao.MovieDao
import dev.brunofelix.movies.core.data.db.mapper.toDomain
import dev.brunofelix.movies.core.data.db.mapper.toEntity
import dev.brunofelix.movies.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val dao: MovieDao
): MovieLocalDataSource {

    override suspend fun insert(movie: Movie): Long {
        return dao.insert(movie.toEntity())
    }

    override suspend fun delete(movie: Movie): Int {
        return dao.delete(movie.toEntity())
    }

    override suspend fun getById(id: Long): Movie? {
        return dao.getById(id)?.toDomain()
    }

    override fun getAll(): Flow<List<Movie>> {
        return dao.getAll().map { entityList ->
            entityList.map { it.toDomain() }
        }
    }
}