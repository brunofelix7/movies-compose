package dev.brunofelix.movies.feature.release.domain.use_case

import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.domain.model.enums.ReleaseType
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.domain.util.map
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
        return repository.getReleases(month, type, page).map { movies ->
            if (type == ReleaseType.THEATERS) movies.releasedIn(month) else movies
        }
    }
}

/**
 * Keeps only the movies whose release date is inside [month].
 *
 * TMDB matches a movie on discover when *any* of its releases of the requested type happens
 * in the window, so titles that premiered long ago still show up in the current month. The
 * calendar is about what opens in that month, so the movie's own release date decides.
 */
private fun List<Movie>.releasedIn(month: ReleaseMonth) = filter { it.releaseDate in month }
