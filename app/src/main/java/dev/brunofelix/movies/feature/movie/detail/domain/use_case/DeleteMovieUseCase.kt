package dev.brunofelix.movies.feature.movie.detail.domain.use_case

import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.repository.MediaRepository
import dev.brunofelix.movies.core.domain.util.exception.LocalException
import javax.inject.Inject

fun interface DeleteMovieUseCase {
    suspend operator fun invoke(media: Media)
}

class DeleteMovieUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : DeleteMovieUseCase {

    override suspend fun invoke(media: Media) {
        try {
            repository.delete(media)
        } catch (e: Exception) {
            throw LocalException.General(R.string.delete_movie_error, e)
        }
    }
}