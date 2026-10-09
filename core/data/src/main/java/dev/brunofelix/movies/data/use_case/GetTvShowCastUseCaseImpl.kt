package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.use_case.GetTvShowCastUseCase
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetTvShowCastUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetTvShowCastUseCase {

    override suspend operator fun invoke(id: Long): Resource<List<Cast>> {
        return repository.getCast(id)
    }
}
