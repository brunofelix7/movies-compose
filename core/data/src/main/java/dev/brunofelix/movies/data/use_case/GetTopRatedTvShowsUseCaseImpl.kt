package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.use_case.GetTopRatedTvShowsUseCase
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetTopRatedTvShowsUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetTopRatedTvShowsUseCase {

    override suspend operator fun invoke(page: Int): Resource<List<TvShow>> {
        return repository.getTopRatedTvShows(page)
    }
}
