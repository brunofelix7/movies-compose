package dev.brunofelix.movies.presentation.navigation

import dev.brunofelix.movies.domain.model.Media
import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.domain.model.enums.MediaType
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class RouteExtTest : DescribeSpec({

    describe("isTopLevel") {
        it("should be true for the bottom bar tabs") {
            TopLevelRoutes.map { it.isTopLevel }.distinct() shouldBe listOf(true)
            TopLevelRoutes shouldBe listOf(Route.Movies, Route.TvShows, Route.Favorites, Route.Releases)
        }

        it("should be false for any other destination and for no destination") {
            listOf(
                Route.Splash,
                Route.Settings,
                Route.MovieDetails(1L),
                Route.TvShowDetails(1L),
                Route.MediaList(MediaListCategory.MOVIE_POPULAR),
                null
            ).map { it.isTopLevel }.distinct() shouldBe listOf(false)
        }
    }

    describe("toDetailRoute") {
        it("should open the movie details for a movie") {
            Media(id = 7L, type = MediaType.MOVIE).toDetailRoute() shouldBe Route.MovieDetails(7L)
        }

        it("should open the TV show details for a TV show") {
            Media(id = 8L, type = MediaType.TV_SHOW).toDetailRoute() shouldBe Route.TvShowDetails(8L)
        }
    }
})
