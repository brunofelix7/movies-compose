package dev.brunofelix.movies.feature.movie.detail.domain.use_case

import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.repository.MediaRepository
import dev.brunofelix.movies.core.domain.util.exception.LocalException
import javax.inject.Inject

fun interface SaveMediaUseCase {
    suspend operator fun invoke(media: Media)
}

class SaveMediaUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : SaveMediaUseCase {

    override suspend operator fun invoke(media: Media) {
        try {
            repository.save(media)
        } catch (e: Exception) {
            throw LocalException.General(R.string.mark_favorite_error, e)
        }
    }
}