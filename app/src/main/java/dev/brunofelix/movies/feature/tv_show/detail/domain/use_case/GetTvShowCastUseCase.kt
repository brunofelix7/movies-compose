package dev.brunofelix.movies.feature.tv_show.detail.domain.use_case

import dev.brunofelix.movies.core.domain.model.Cast
import dev.brunofelix.movies.core.domain.repository.TvShowRepository
import dev.brunofelix.movies.core.domain.util.Resource
import javax.inject.Inject

fun interface GetTvShowCastUseCase {
    suspend operator fun invoke(id: Long): Resource<List<Cast>>
}

class GetTvShowCastUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetTvShowCastUseCase {
    override suspend fun invoke(id: Long): Resource<List<Cast>> {
        return repository.getCast(id)
    }
}
