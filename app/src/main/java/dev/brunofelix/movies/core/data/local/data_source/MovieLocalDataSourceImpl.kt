package dev.brunofelix.movies.core.data.local.data_source

import dev.brunofelix.movies.core.data.local.MovieDatabase
import dev.brunofelix.movies.core.data.local.entity.MovieEntity
import dev.brunofelix.movies.core.domain.data_source.MovieLocalDataSource
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val db: MovieDatabase
): MovieLocalDataSource {
    override suspend fun insert(entity: MovieEntity) = db.movieDao.insert(entity)

    override suspend fun delete(entity: MovieEntity) = db.movieDao.delete(entity)

    override suspend fun getById(id: Long) = db.movieDao.getById(id)

    override fun getAll() = db.movieDao.getAll()
}