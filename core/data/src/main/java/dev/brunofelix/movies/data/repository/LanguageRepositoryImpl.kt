package dev.brunofelix.movies.data.repository

import dev.brunofelix.movies.data.local.source.LanguageLocalDataSource
import dev.brunofelix.movies.domain.model.enums.LanguageEnum
import dev.brunofelix.movies.domain.repository.LanguageRepository
import dev.brunofelix.movies.domain.util.Resource
import dev.brunofelix.movies.domain.util.toResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of [LanguageRepository].
 *
 * @property localDataSource The source that persists the user's language choice.
 */
class LanguageRepositoryImpl @Inject constructor(
    private val localDataSource: LanguageLocalDataSource
) : LanguageRepository {

    override fun getLanguage(): Flow<LanguageEnum> = localDataSource.observe()

    override suspend fun saveLanguage(language: LanguageEnum): Resource<Unit> {
        return localDataSource.save(language).toResource()
    }
}
