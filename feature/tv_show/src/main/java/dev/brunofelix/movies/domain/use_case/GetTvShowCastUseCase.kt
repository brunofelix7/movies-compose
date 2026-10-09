package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Cast
import dev.brunofelix.movies.domain.util.Resource

fun interface GetTvShowCastUseCase {
    suspend operator fun invoke(id: Long): Resource<List<Cast>>
}
