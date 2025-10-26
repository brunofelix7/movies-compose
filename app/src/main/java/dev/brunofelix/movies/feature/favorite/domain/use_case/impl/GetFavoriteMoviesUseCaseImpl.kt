package dev.brunofelix.movies.feature.favorite.domain.use_case.impl

import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import dev.brunofelix.movies.core.util.exception.LocalException
import dev.brunofelix.movies.feature.favorite.domain.use_case.GetFavoriteMoviesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetFavoriteMoviesUseCase {

    override operator fun invoke(): Flow<List<Movie>> {
        try {
            return repository.fetchFavorites()
        } catch (_: Exception) {
            throw LocalException()
        }
    }
}