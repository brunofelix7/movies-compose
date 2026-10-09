package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class SaveMediaUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : SaveMediaUseCase {

    override suspend operator fun invoke(media: Media): Resource<Unit> {
        return repository.save(media)
    }
}
