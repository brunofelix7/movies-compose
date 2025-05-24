package dev.brunofelix.pmovie.feature.movie.domain.use_case

import dev.brunofelix.pmovie.core.util.exception.LocalException
import dev.brunofelix.pmovie.feature.movie.domain.model.Movie
import dev.brunofelix.pmovie.feature.movie.domain.repository.MovieRepository
import javax.inject.Inject

fun interface DeleteFromFavoritesUseCase {
    suspend operator fun invoke(movie: Movie)
}

class DeleteFromFavoritesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository
) : DeleteFromFavoritesUseCase {

    override suspend fun invoke(movie: Movie) {
        try {
            repository.deleteFromFavorites(movie)
        } catch (_: Exception) {
            throw LocalException("Failed to delete the movie.")
        }
    }
}