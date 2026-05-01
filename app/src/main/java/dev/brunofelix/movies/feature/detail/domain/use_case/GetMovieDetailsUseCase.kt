package dev.brunofelix.movies.feature.detail.domain.use_case

import dev.brunofelix.movies.core.data.util.Resource
import dev.brunofelix.movies.core.domain.model.Movie

fun interface GetMovieDetailsUseCase {
    suspend operator fun invoke(id: Long): Resource<Movie>
}