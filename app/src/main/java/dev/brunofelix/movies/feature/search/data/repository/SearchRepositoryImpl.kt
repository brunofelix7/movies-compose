package dev.brunofelix.movies.feature.search.data.repository

import dev.brunofelix.movies.core.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.data.remote.source.TvShowRemoteDataSource
import dev.brunofelix.movies.core.domain.mapper.toMovieMediaList
import dev.brunofelix.movies.core.domain.mapper.toTvShowMediaList
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.domain.util.toResource
import dev.brunofelix.movies.feature.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource
) : SearchRepository {

    override suspend fun search(
        query: String,
        page: Int
    ): Resource<List<Media>> {
        return runCatching {
            val movies = movieRemoteDataSource.search(query, page).getOrThrow()
            val tvShows = tvShowRemoteDataSource.search(query, page).getOrThrow()
            movies.toMovieMediaList() + tvShows.toTvShowMediaList()
        }.toResource()
    }
}
