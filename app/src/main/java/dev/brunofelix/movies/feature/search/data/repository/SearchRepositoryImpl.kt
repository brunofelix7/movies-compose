package dev.brunofelix.movies.feature.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.brunofelix.movies.core.data.remote.MovieService
import dev.brunofelix.movies.core.data.remote.TvShowService
import dev.brunofelix.movies.core.data.remote.mapper.toDomainList
import dev.brunofelix.movies.core.domain.mapper.toMovieMediaList
import dev.brunofelix.movies.core.domain.mapper.toTvShowMediaList
import dev.brunofelix.movies.core.data.remote.paging.BasePagingSource
import dev.brunofelix.movies.core.data.util.extension.mapOrThrow
import dev.brunofelix.movies.core.data.util.extension.toRemoteException
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val tvShowService: TvShowService
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
                        val movieResponse = movieService.search(query, page)
                        val tvShowResponse = tvShowService.search(query, page)
                        val movies = movieResponse.mapOrThrow {
                            it.toDomainList().toMovieMediaList()
                        }
                        val tvShows = tvShowResponse.mapOrThrow {
                            it.toDomainList().toTvShowMediaList()
                        }
                        movies + tvShows
                    }.recoverCatching { throw it.toRemoteException() }
                }
            }
        ).flow
    }
}
