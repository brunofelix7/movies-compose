package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.repository.LanguageRepository
import dev.brunofelix.movies.domain.util.Resource
import javax.inject.Inject

class SaveLanguageUseCaseImpl @Inject constructor(
    private val repository: LanguageRepository
) : SaveLanguageUseCase {

    override suspend operator fun invoke(language: LanguageEnum): Resource<Unit> {
        return repository.saveLanguage(language)
    }
}
