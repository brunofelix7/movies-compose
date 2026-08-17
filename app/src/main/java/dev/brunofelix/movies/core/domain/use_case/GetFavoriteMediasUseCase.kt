package dev.brunofelix.movies.core.domain.use_case

import dev.brunofelix.movies.core.domain.model.Media
import dev.brunofelix.movies.core.domain.repository.MediaRepository
import dev.brunofelix.movies.core.domain.util.exception.LocalException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface GetFavoriteMediasUseCase {
    operator fun invoke(): Flow<List<Media>>
}

class GetFavoriteMediasUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : GetFavoriteMediasUseCase {

    override operator fun invoke(): Flow<List<Media>> {
        try {
            return repository.getFavoriteMedias()
        } catch (_: Exception) {
            throw LocalException.Unknown()
        }
    }
}