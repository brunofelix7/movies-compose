package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.repository.TvShowRepository
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class GetTvShowDetailUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository
) : GetTvShowDetailUseCase {

    override suspend operator fun invoke(id: Long): Resource<TvShow> {
        return repository.getDetails(id)
    }
}
