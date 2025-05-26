package dev.brunofelix.movies.feature.movie.domain.use_case

import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.util.exception.LocalException
import dev.brunofelix.movies.feature.movie.domain.model.Movie
import dev.brunofelix.movies.feature.movie.domain.repository.MovieRepository
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
        } catch (e: Exception) {
            throw LocalException(R.string.delete_movie_error, e)
        }
    }
}