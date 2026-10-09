package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Movie
import dev.brunofelix.movies.domain.util.Resource

fun interface GetMovieDetailUseCase {
    suspend operator fun invoke(id: Long): Resource<Movie>
}
