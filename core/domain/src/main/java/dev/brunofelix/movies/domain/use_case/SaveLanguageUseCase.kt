package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.util.Resource

fun interface SaveLanguageUseCase {
    suspend operator fun invoke(language: LanguageEnum): Resource<Unit>
}
