package dev.brunofelix.pmovie.feature.movie.domain.use_case

import dev.brunofelix.pmovie.core.util.exception.LocalException
import dev.brunofelix.pmovie.feature.movie.domain.model.Movie
import dev.brunofelix.pmovie.feature.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

fun interface DeleteFromFavoritesUseCase {
    operator fun invoke(movie: Movie): Flow<Unit>
}

class DeleteFromFavoritesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : DeleteFromFavoritesUseCase {

    override operator fun invoke(movie: Movie): Flow<Unit> = flow {
        try {
            emit(repository.deleteFromFavorites(movie))
        } catch (e: Exception) {
            throw LocalException("Failed to delete movie.")
        }
    }
}