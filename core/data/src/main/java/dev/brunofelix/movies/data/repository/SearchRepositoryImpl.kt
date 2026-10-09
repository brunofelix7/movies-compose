package dev.brunofelix.movies.data.repository

import dev.brunofelix.movies.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.data.remote.source.TvShowRemoteDataSource
import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.repository.SearchRepository
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.extension.toMovieMediaList
import dev.brunofelix.movies.domain.util.extension.toTvShowMediaList
import javax.inject.Inject

/**
 * Implementation of [SearchRepository] that searches movies and TV shows for the same page and
 * returns them together, movies first.
 */
class SearchRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource
) : SearchRepository {

    override suspend fun search(
        query: String,
        page: Int
    ): Resource<List<Media>> {
        val movies = movieRemoteDataSource.search(query, page).getOrElse { return Resource.Error(it) }
        val tvShows = tvShowRemoteDataSource.search(query, page).getOrElse { return Resource.Error(it) }
        return Resource.Success(movies.toMovieMediaList() + tvShows.toTvShowMediaList())
    }
}
