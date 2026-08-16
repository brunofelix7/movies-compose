package dev.brunofelix.movies.feature.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.data.remote.paging.BasePagingSource
import dev.brunofelix.movies.core.data.remote.source.MovieRemoteDataSource
import dev.brunofelix.movies.core.data.remote.source.TvShowRemoteDataSource
import dev.brunofelix.movies.core.domain.mapper.toMovieMediaList
import dev.brunofelix.movies.core.domain.mapper.toTvShowMediaList
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource
) : SearchRepository {

    override fun search(
        query: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Media>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                BasePagingSource(pagingConfig.pageSize) { page ->
                    runCatching {
                        val movies = movieRemoteDataSource.search(query, page).getOrThrow()
                        val tvShows = tvShowRemoteDataSource.search(query, page).getOrThrow()
                        movies.toMovieMediaList() + tvShows.toTvShowMediaList()
                    }
                }
            }
        ).flow
    }
}
