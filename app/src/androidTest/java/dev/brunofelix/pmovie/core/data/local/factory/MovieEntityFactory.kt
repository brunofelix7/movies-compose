package dev.brunofelix.pmovie.core.data.local.factory

import dev.brunofelix.pmovie.core.data.local.entity.MovieEntity

object MovieEntityFactory {

    fun create(id: Long): MovieEntity {
        return MovieEntity(id = id, title = "Movie $id", imageUrl = "URL $id", voteAverage = 1F, duration = 1)
    }

    fun createList(): List<MovieEntity> {
        return listOf(
            MovieEntity(id = 2, title = "Movie 5", imageUrl = "URL 2", voteAverage = 1F, duration = 1),
            MovieEntity(id = 5, title = "Movie 3", imageUrl = "URL 5", voteAverage = 1F, duration = 1),
            MovieEntity(id = 1, title = "Movie 4", imageUrl = "URL 1", voteAverage = 1F, duration = 1),
            MovieEntity(id = 4, title = "Movie 2", imageUrl = "URL 4", voteAverage = 1F, duration = 1),
            MovieEntity(id = 3, title = "Movie 1", imageUrl = "URL 3", voteAverage = 1F, duration = 1)
        )
    }
}