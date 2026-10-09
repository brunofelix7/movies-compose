package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.util.Resource

fun interface GetTopRatedMoviesUseCase {
    suspend operator fun invoke(page: Int): Resource<List<Movie>>
}
