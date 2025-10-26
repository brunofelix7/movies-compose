package dev.brunofelix.movies.feature.favorite.domain.use_case

import dev.brunofelix.movies.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

fun interface GetFavoriteMoviesUseCase {
    operator fun invoke(): Flow<List<Movie>>
}