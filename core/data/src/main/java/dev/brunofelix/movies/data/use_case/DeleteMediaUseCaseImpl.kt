package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.use_case.DeleteMediaUseCase
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class DeleteMediaUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : DeleteMediaUseCase {

    override suspend operator fun invoke(media: Media): Resource<Unit> {
        return repository.delete(media)
    }
}
