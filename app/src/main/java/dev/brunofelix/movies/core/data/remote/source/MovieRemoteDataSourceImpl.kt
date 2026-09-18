package dev.brunofelix.movies.core.data.remote.source

import dev.brunofelix.movies.core.data.remote.MovieService
import dev.brunofelix.movies.core.data.remote.mapper.toCastList
import dev.brunofelix.movies.core.data.remote.mapper.toDomain
import dev.brunofelix.movies.core.data.remote.mapper.toDomainList
import dev.brunofelix.movies.core.data.util.BaseRemoteDataSource
import dev.brunofelix.movies.core.domain.model.Cast
import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.model.Video
import dev.brunofelix.movies.core.domain.model.enums.ReleaseType
import javax.inject.Inject

/**
 * Implementation of [MovieRemoteDataSource] using [MovieService].
 * @property service The Retrofit service for movie API calls.
 */
class MovieRemoteDataSourceImpl @Inject constructor(
    service: MovieService
) : BaseRemoteDataSource<MovieService>(service), MovieRemoteDataSource {

    override suspend fun getPopulars(page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { getPopulars(page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun getUpcoming(page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { getUpcoming(page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun getTopRated(page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { getTopRated(page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun search(query: String, page: Int): Result<List<Movie>> {
        return safeApiCall(
            call = { search(query, page) },
            transform = { it.toDomainList() }
        )
    }

    override suspend fun getDetails(id: Long): Result<Movie> {
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

    override suspend fun getReleases(
        startDate: String,
        endDate: String,
        type: ReleaseType,
        page: Int
    ): Result<List<Movie>> {
        return safeApiCall(
            call = {
                discover(
                    startDate = startDate,
                    endDate = endDate,
                    releaseType = type.query,
                    sortBy = SORT_BY_POPULARITY,
                    page = page
                )
            },
            transform = { it.toDomainList() }
        )
    }
}

private const val SORT_BY_POPULARITY = "popularity.desc"

/** TMDB release type codes: 2 limited theatrical, 3 theatrical, 4 digital. */
private val ReleaseType.query: String
    get() = when (this) {
        ReleaseType.THEATERS -> "2|3"
        ReleaseType.STREAMING -> "4"
    }
