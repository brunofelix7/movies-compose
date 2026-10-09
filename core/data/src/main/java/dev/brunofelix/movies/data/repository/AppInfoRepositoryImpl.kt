package dev.brunofelix.movies.data.repository

import dev.brunofelix.movies.data.local.source.AppInfoLocalDataSource
import dev.brunofelix.movies.domain.repository.AppInfoRepository
import javax.inject.Inject

/**
 * Implementation of [AppInfoRepository].
 *
 * @property localDataSource The source for information about the installed app.
 */
class AppInfoRepositoryImpl @Inject constructor(
    private val localDataSource: AppInfoLocalDataSource
) : AppInfoRepository {

    override fun getAppVersion(): String = localDataSource.getVersionName()
}
