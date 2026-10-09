package dev.brunofelix.movies.data.remote.source

import dev.brunofelix.movies.data.remote.TvShowApi
import dev.brunofelix.movies.data.remote.mapper.toCastList
import dev.brunofelix.movies.data.remote.mapper.toDomain
import dev.brunofelix.movies.data.remote.mapper.toDomainList
import dev.brunofelix.movies.data.remote.mapper.toEpisodeList
import dev.brunofelix.movies.data.remote.mapper.toWatchProviderList
import dev.brunofelix.movies.data.util.BaseRemoteDataSource
import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.model.Episode
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.model.Video
import dev.brunofelix.movies.domain.model.WatchProvider
import javax.inject.Inject

/**
 * Implementation of [TvShowRemoteDataSource] using [TvShowApi].
 * @property api The Retrofit API for TV show API calls.
 */
class TvShowRemoteDataSourceImpl @Inject constructor(
    api: TvShowApi
) : BaseRemoteDataSource<TvShowApi>(api), TvShowRemoteDataSource {

    override suspend fun getPopulars(page: Int): Result<List<TvShow>> {
        return safeApiCall(
            call = { getPopulars(page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun getTopRated(page: Int): Result<List<TvShow>> {
        return safeApiCall(
            call = { getTopRated(page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun search(query: String, page: Int): Result<List<TvShow>> {
        return safeApiCall(
            call = { search(query, page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun getDetails(id: Long): Result<TvShow> {
        return safeApiCall(
            call = { getDetails(id) },
            transform = { it.toDomain() }
        )
    }

    override suspend fun getVideos(id: Long): Result<List<Video>> {
        return safeApiCall(
            call = { getVideos(id) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun getCast(id: Long): Result<List<Cast>> {
        return safeApiCall(
            call = { getCredits(id) },
            transform = { it.toCastList() }
        )
    }

    override suspend fun getWatchProviders(id: Long, region: String): Result<List<WatchProvider>> {
        return safeApiCall(
            call = { getWatchProviders(id) },
            transform = { it.toWatchProviderList(region) }
        )
    }

    override suspend fun getSeasonEpisodes(id: Long, seasonNumber: Int): Result<List<Episode>> {
        return safeApiCall(
            call = { getSeason(id, seasonNumber) },
            transform = { it.toEpisodeList() }
        )
    }

    override suspend fun getReleases(
        startDate: String,
        endDate: String,
        page: Int
    ): Result<List<TvShow>> {
        return safeApiCall(
            call = {
                discover(
                    startDate = startDate,
                    endDate = endDate,
                    sortBy = SORT_BY_POPULARITY,
                    page = page
                )
            },
            transform = { it.toDomainList() }
        )
    }
}

private const val SORT_BY_POPULARITY = "popularity.desc"
