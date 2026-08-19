package dev.brunofelix.movies.feature.tv_show.home.domain.use_case

import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import dev.brunofelix.movies.core.domain.util.Resource
import javax.inject.Inject

fun interface GetPopularTvShowsUseCase {
    suspend operator fun invoke(page: Int): Resource<List<TvShow>>
}

class GetPopularTvShowsUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetPopularTvShowsUseCase {
    override suspend fun invoke(page: Int): Resource<List<TvShow>> {
        return repository.getPopularTvShows(page)
    }
}
