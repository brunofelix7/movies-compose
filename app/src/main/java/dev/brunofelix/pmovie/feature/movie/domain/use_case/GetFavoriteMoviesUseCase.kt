package dev.brunofelix.pmovie.feature.movie.domain.use_case

import dev.brunofelix.pmovie.feature.movie.domain.model.Movie
import dev.brunofelix.pmovie.feature.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface GetFavoriteMoviesUseCase {
    operator fun invoke(): Flow<List<Movie>?>
}

class GetFavoriteMoviesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : GetFavoriteMoviesUseCase {

    override operator fun invoke(): Flow<List<Movie>?> {
        return repository.fetchFavorites()
    }
}