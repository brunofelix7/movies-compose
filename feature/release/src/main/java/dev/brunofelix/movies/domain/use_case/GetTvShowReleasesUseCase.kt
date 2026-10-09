package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.TvShow
import dev.brunofelix.movies.domain.util.Resource

fun interface GetTvShowReleasesUseCase {
    suspend operator fun invoke(month: ReleaseMonth, page: Int): Resource<List<TvShow>>
}
