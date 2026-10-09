package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.use_case.GetTvShowReleasesUseCase
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetTvShowReleasesUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetTvShowReleasesUseCase {

    override suspend operator fun invoke(month: ReleaseMonth, page: Int): Resource<List<TvShow>> {
        return repository.getReleases(month, page)
    }
}
