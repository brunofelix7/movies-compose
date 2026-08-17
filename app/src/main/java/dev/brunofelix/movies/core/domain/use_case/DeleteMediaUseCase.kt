package dev.brunofelix.movies.core.domain.use_case

import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.repository.MediaRepository
import dev.brunofelix.movies.core.domain.util.exception.LocalException
import javax.inject.Inject

fun interface DeleteMediaUseCase {
    suspend operator fun invoke(media: Media)
}

class DeleteMediaUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : DeleteMediaUseCase {

    override suspend fun invoke(media: Media) {
        try {
            repository.delete(media)
        } catch (e: Exception) {
            throw LocalException.General(R.string.delete_media_error, e)
        }
    }
}