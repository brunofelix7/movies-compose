package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.repository.MediaRepository
import dev.brunofelix.movies.domain.use_case.GetFavoriteMediasUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMediasUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : GetFavoriteMediasUseCase {

    override operator fun invoke(): Flow<List<Media>> {
        return repository.getFavoriteMedias()
    }
}
