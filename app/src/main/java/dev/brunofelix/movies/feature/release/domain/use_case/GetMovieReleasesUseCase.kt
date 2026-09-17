package dev.brunofelix.movies.feature.release.domain.use_case

import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.domain.model.enums.ReleaseType
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.util.Resource
import javax.inject.Inject

fun interface GetMovieReleasesUseCase {
    suspend operator fun invoke(
        month: ReleaseMonth,
        type: ReleaseType,
        page: Int
    ): Resource<List<Movie>>
}

class GetMovieReleasesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetMovieReleasesUseCase {
    override suspend fun invoke(
        month: ReleaseMonth,
        type: ReleaseType,
        page: Int
    ): Resource<List<Movie>> {
        return repository.getReleases(month, type, page)
    }
}
