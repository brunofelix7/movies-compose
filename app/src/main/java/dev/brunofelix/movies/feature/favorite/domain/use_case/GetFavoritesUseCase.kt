package dev.brunofelix.movies.feature.favorite.domain.use_case

import dev.brunofelix.movies.core.domain.model.Movie
import dev.brunofelix.movies.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface GetFavoritesUseCase {
    operator fun invoke(): Flow<List<Movie>?>
}

class GetFavoritesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetFavoritesUseCase {

    override operator fun invoke(): Flow<List<Movie>?> {
        return repository.fetchFavorites()
    }
}