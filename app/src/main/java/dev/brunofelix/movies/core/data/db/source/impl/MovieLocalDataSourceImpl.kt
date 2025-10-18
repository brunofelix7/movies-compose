package dev.brunofelix.movies.core.data.db.source.impl

import dev.brunofelix.movies.core.data.db.MovieDatabase
import dev.brunofelix.movies.core.data.db.entity.MovieEntity
import dev.brunofelix.movies.core.data.source.MovieLocalDataSource
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val db: MovieDatabase
): MovieLocalDataSource {
    override suspend fun insert(entity: MovieEntity) = db.movieDao.insert(entity)

    override suspend fun delete(entity: MovieEntity) = db.movieDao.delete(entity)

    override suspend fun getById(id: Long) = db.movieDao.getById(id)

    override fun getAll() = db.movieDao.getAll()
}