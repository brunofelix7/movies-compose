package dev.brunofelix.movies.core.data.repository

import dev.brunofelix.movies.core.data.remote.source.TvShowRemoteDataSource
import dev.brunofelix.movies.core.data.util.Resource
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import dev.brunofelix.movies.core.util.extension.toResource
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val remoteDataSource: TvShowRemoteDataSource
) : TvShowRepository {

    override suspend fun getPopulars(page: Int): Resource<List<TvShow>> {
        return remoteDataSource.getPopular(page).toResource()
    }

    override suspend fun getTopRated(page: Int): Resource<List<TvShow>> {
        return remoteDataSource.getTopRated(page).toResource()
    }

    override suspend fun getDetails(id: Long): Resource<TvShow> {
        return remoteDataSource.getDetails(id).toResource()
    }
}
