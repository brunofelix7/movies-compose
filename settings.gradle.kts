pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "movies-explorer"
include(":app")
include(":core:domain")
include(":core:data")
include(":core:presentation")
include(":core:designsystem")
include(":feature:splash")
include(":feature:movie")
include(":feature:tv_show")
include(":feature:favorite")
include(":feature:release")
include(":feature:search")
include(":feature:settings")
include(":feature:media_list")
