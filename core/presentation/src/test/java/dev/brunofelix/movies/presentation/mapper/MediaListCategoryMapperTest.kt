package dev.brunofelix.movies.presentation.mapper

import dev.brunofelix.movies.domain.model.enums.MediaListCategory
import dev.brunofelix.movies.core.presentation.R
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class MediaListCategoryMapperTest : DescribeSpec({

    describe("titleResId") {
        it("should map every category to its title") {
            MediaListCategory.entries.associateWith { it.titleResId } shouldBe mapOf(
                MediaListCategory.MOVIE_POPULAR to R.string.popular,
                MediaListCategory.MOVIE_UPCOMING to R.string.upcoming,
                MediaListCategory.MOVIE_TOP_RATED to R.string.top_rated,
                MediaListCategory.TV_SHOW_POPULAR to R.string.popular,
                MediaListCategory.TV_SHOW_TOP_RATED to R.string.top_rated,
                MediaListCategory.RELEASE_THEATERS to R.string.releases_theaters,
                MediaListCategory.RELEASE_STREAMING to R.string.releases_streaming,
                MediaListCategory.RELEASE_SERIES to R.string.releases_series
            )
        }
    }
})
