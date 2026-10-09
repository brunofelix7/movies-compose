package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.util.Resource

fun interface GetTvShowDetailUseCase {
    suspend operator fun invoke(id: Long): Resource<TvShow>
}
