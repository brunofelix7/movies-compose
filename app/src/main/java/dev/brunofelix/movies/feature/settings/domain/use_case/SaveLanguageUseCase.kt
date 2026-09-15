package dev.brunofelix.movies.feature.settings.domain.use_case

import dev.brunofelix.movies.core.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.core.domain.repository.LanguageRepository
import dev.brunofelix.movies.core.domain.util.exception.LocalException
import javax.inject.Inject

fun interface SaveLanguageUseCase {
    suspend operator fun invoke(language: LanguageEnum)
}

class SaveLanguageUseCaseImpl @Inject constructor(
    private val repository: LanguageRepository
) : SaveLanguageUseCase {

    override suspend operator fun invoke(language: LanguageEnum) {
        try {
            repository.saveLanguage(language)
        } catch (_: Exception) {
            throw LocalException.Unknown()
        }
    }
}
