package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.util.Resource

fun interface GetTopRatedTvShowsUseCase {
    suspend operator fun invoke(page: Int): Resource<List<TvShow>>
}
