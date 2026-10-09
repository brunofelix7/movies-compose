package dev.brunofelix.movies.presentation.viewmodel

import dev.brunofelix.movies.presentation.navigation.Route
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class NavigationViewModelTest : DescribeSpec({

    lateinit var viewModel: NavigationViewModel

    beforeTest { viewModel = NavigationViewModel() }

    describe("initial state") {
        it("should start on the splash") {
            viewModel.backStack.value shouldBe listOf(Route.Splash)
        }
    }

    describe("navigateTo") {
        it("should push the route on top of the back stack") {
            viewModel.navigateTo(Route.Settings)

            viewModel.backStack.value shouldBe listOf(Route.Splash, Route.Settings)
        }

        it("should not push the same route twice in a row") {
            viewModel.navigateTo(Route.MovieDetails(1L))
            viewModel.navigateTo(Route.MovieDetails(1L))

            viewModel.backStack.value shouldBe listOf(Route.Splash, Route.MovieDetails(1L))
        }
    }

    describe("replaceCurrent") {
        it("should replace the top of the back stack") {
            viewModel.replaceCurrent(Route.Movies)
            viewModel.replaceCurrent(Route.TvShows)

            viewModel.backStack.value shouldBe listOf(Route.TvShows)
        }
    }

    describe("popBackStack") {
        it("should remove the top route") {
            viewModel.replaceCurrent(Route.Movies)
            viewModel.navigateTo(Route.Settings)

            viewModel.popBackStack()

            viewModel.backStack.value shouldBe listOf(Route.Movies)
        }

        it("should keep the last route") {
            viewModel.popBackStack()

            viewModel.backStack.value shouldBe listOf(Route.Splash)
        }
    }
})
