package dev.brunofelix.movies.data.use_case

import dev.brunofelix.movies.domain.repository.AppInfoRepository
import dev.brunofelix.movies.domain.use_case.GetAppVersionUseCase
import javax.inject.Inject

class GetAppVersionUseCaseImpl @Inject constructor(
    private val repository: AppInfoRepository
) : GetAppVersionUseCase {

    override operator fun invoke(): String {
        return repository.getAppVersion()
    }
}
