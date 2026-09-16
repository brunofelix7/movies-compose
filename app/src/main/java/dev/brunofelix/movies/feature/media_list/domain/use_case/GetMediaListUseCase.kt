package dev.brunofelix.movies.feature.media_list.domain.use_case

import dev.brunofelix.movies.core.domain.mapper.toMovieMediaList
import dev.brunofelix.movies.core.domain.mapper.toTvShowMediaList
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.core.domain.model.enums.ReleaseType
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import dev.brunofelix.movies.core.domain.util.Resource
import dev.brunofelix.movies.core.domain.util.map
import javax.inject.Inject

/**
 * Loads one page of any of the lists reachable from a "View more" action.
 */
fun interface GetMediaListUseCase {
    suspend operator fun invoke(
        category: MediaListCategory,
        month: ReleaseMonth?,
        page: Int
    ): Resource<List<Media>>
}

class GetMediaListUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvShowRepository: TvShowRepository
) : GetMediaListUseCase {

    override suspend fun invoke(
        category: MediaListCategory,
        month: ReleaseMonth?,
        page: Int
    ): Resource<List<Media>> {
        val releaseMonth = month ?: ReleaseMonth.current()

        return when (category) {
            MediaListCategory.MOVIE_POPULAR ->
                movieRepository.getPopularMovies(page).map { it.toMovieMediaList() }

            MediaListCategory.MOVIE_UPCOMING ->
                movieRepository.getUpcomingMovies(page).map { it.toMovieMediaList() }

            MediaListCategory.MOVIE_TOP_RATED ->
                movieRepository.getTopRatedMovies(page).map { it.toMovieMediaList() }

            MediaListCategory.TV_SHOW_POPULAR ->
                tvShowRepository.getPopularTvShows(page).map { it.toTvShowMediaList() }

            MediaListCategory.TV_SHOW_TOP_RATED ->
                tvShowRepository.getTopRatedTvShows(page).map { it.toTvShowMediaList() }

            MediaListCategory.RELEASE_THEATERS ->
                movieRepository.getReleases(releaseMonth, ReleaseType.THEATERS, page)
                    .map { it.toMovieMediaList() }

            MediaListCategory.RELEASE_STREAMING ->
                movieRepository.getReleases(releaseMonth, ReleaseType.STREAMING, page)
                    .map { it.toMovieMediaList() }

            MediaListCategory.RELEASE_SERIES ->
                tvShowRepository.getReleases(releaseMonth, page)
                    .map { it.toTvShowMediaList() }
        }
    }
}
