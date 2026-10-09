package dev.brunofelix.movies.domain.use_case

fun interface GetAppVersionUseCase {
    operator fun invoke(): String
}
