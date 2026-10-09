package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.model.ReleaseMonth
import dev.brunofelix.movies.domain.model.enums.ReleaseType
import dev.brunofelix.movies.domain.util.Resource

fun interface GetMovieReleasesUseCase {
    suspend operator fun invoke(month: ReleaseMonth, type: ReleaseType, page: Int): Resource<List<Movie>>
}
