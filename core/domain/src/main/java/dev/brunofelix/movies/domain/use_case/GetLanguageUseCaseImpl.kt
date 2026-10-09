package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguageUseCaseImpl @Inject constructor(
    private val repository: LanguageRepository
) : GetLanguageUseCase {

    override operator fun invoke(): Flow<LanguageEnum> {
        return repository.getLanguage()
    }
}
