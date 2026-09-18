package dev.brunofelix.movies.core.data.remote.source

import dev.brunofelix.movies.core.data.remote.TvShowService
import dev.brunofelix.movies.core.data.remote.mapper.toCastList
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.mapper.toDomainList
import dev.brunofelix.movies.core.data.remote.mapper.toEpisodeList
import dev.brunofelix.movies.core.data.util.BaseRemoteDataSource
import dev.brunofelix.movies.core.domain.model.Cast
import dev.brunofelix.movies.core.domain.model.Episode
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.model.Video
import javax.inject.Inject

/**
 * Implementation of [TvShowRemoteDataSource] using [TvShowService].
 * @property service The Retrofit service for TV show API calls.
 */
class TvShowRemoteDataSourceImpl @Inject constructor(
    service: TvShowService
) : BaseRemoteDataSource<TvShowService>(service), TvShowRemoteDataSource {

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
