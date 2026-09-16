package dev.brunofelix.movies.feature.tv_show.detail.domain.use_case

import dev.brunofelix.movies.core.domain.model.TvShow
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import dev.brunofelix.movies.core.domain.util.Resource
import javax.inject.Inject

fun interface GetTvShowDetailUseCase {
    suspend operator fun invoke(id: Long): Resource<TvShow>
}

class GetTvShowDetailUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetTvShowDetailUseCase {

    override suspend operator fun invoke(id: Long): Resource<TvShow> {
        return repository.getDetails(id)
    }
}
