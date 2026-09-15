package dev.brunofelix.movies.feature.settings.domain.use_case

import dev.brunofelix.movies.core.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.core.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

fun interface GetLanguageUseCase {
    operator fun invoke(): Flow<LanguageEnum>
}

class GetLanguageUseCaseImpl @Inject constructor(
    private val repository: LanguageRepository
) : GetLanguageUseCase {

    override operator fun invoke(): Flow<LanguageEnum> {
        return repository.getLanguage()
    }
}
