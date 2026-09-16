package dev.brunofelix.movies.feature.release.domain.use_case

import dev.brunofelix.movies.core.domain.model.ReleaseMonth
import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import dev.brunofelix.movies.core.domain.util.Resource
import javax.inject.Inject

fun interface GetTvShowReleasesUseCase {
    suspend operator fun invoke(month: ReleaseMonth, page: Int): Resource<List<TvShow>>
}

class GetTvShowReleasesUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetTvShowReleasesUseCase {
    override suspend fun invoke(month: ReleaseMonth, page: Int): Resource<List<TvShow>> {
        return repository.getReleases(month, page)
    }
}
