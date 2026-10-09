package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetPopularTvShowsUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetPopularTvShowsUseCase {

    override suspend operator fun invoke(page: Int): Resource<List<TvShow>> {
        return repository.getPopularTvShows(page)
    }
}
