package dev.brunofelix.movies.test_util.factory

import dev.brunofelix.movies.core.data.local.db.entity.MediaEntity

object MovieEntityFactory {

    fun create(id: Long): MediaEntity {
        return MediaEntity(id = id, title = "Movie $id", posterPath = "URL $id", voteAverage = 1F, duration = 1, releaseDate = "")
    }

    fun createList(): List<MediaEntity> {
        return listOf(
            MediaEntity(id = 2, title = "Movie 5", posterPath = "URL 2", voteAverage = 1F, duration = 1, releaseDate = ""),
            MediaEntity(id = 5, title = "Movie 3", posterPath = "URL 5", voteAverage = 1F, duration = 1, releaseDate = ""),
            MediaEntity(id = 1, title = "Movie 4", posterPath = "URL 1", voteAverage = 1F, duration = 1, releaseDate = ""),
            MediaEntity(id = 4, title = "Movie 2", posterPath = "URL 4", voteAverage = 1F, duration = 1, releaseDate = ""),
            MediaEntity(id = 3, title = "Movie 1", posterPath = "URL 3", voteAverage = 1F, duration = 1, releaseDate = "")
        )
    }
}