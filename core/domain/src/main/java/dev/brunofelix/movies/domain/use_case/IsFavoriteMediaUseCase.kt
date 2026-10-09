package dev.brunofelix.movies.domain.use_case

import dev.brunofelix.movies.domain.util.Resource

fun interface IsFavoriteMediaUseCase {
    suspend operator fun invoke(id: Long): Resource<Boolean>
}
