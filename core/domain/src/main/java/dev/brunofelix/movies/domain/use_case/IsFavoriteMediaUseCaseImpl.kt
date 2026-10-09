package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class IsFavoriteMediaUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : IsFavoriteMediaUseCase {

    override suspend operator fun invoke(id: Long): Resource<Boolean> {
        return repository.isFavorite(id)
    }
}
