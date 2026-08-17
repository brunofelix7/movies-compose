package dev.brunofelix.movies.feature.movie.detail.domain.use_case

import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.repository.MediaRepository
import dev.brunofelix.movies.core.domain.util.exception.LocalException
import javax.inject.Inject

fun interface IsFavoriteMediaUseCase {
    suspend operator fun invoke(id: Long): Boolean
}

class IsFavoriteMediaUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : IsFavoriteMediaUseCase {

    override suspend operator fun invoke(id: Long): Boolean {
        return try {
            repository.isFavorite(id)
        } catch (e: Exception) {
            throw LocalException.General(R.string.is_favorite_movie_error, e)
        }
    }
}