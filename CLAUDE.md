# Project Instructions - Claude & Cursor

This file is the primary instruction set for Claude in Cursor (and Claude Code). Every section is mandatory.

The architecture standards in `.cursor/rules/` are project-agnostic and always applied in Cursor. They use placeholders (`<basePackage>`, `AppTheme`, `AppFontFamily`, "the design source") that resolve to the values in the **Project Profile** below. If this file and a rule disagree, stop and ask the user instead of picking one.

> **Reusing in another project**: copy `.cursor/rules/` and `.cursor/templates/` unchanged (plus `.agents/` and `GEMINI.md` if you use Antigravity), copy this file, and rewrite only **Part A**. Part B is the same in every project.

| Rule | Covers |
|---|---|
| `android-engineer.mdc` | Kotlin style, banned legacy tech, MVI, Hilt, testing stack |
| `android-architecture.mdc` | Module layout, dependency rules, Gradle conventions |
| `android-domain-layer.mdc` | Models, repository interfaces, `fun interface` use cases, `Resource<T>` |
| `android-data-layer.mdc` | DTOs, entities, mappers, data sources, repositories |
| `android-di.mdc` | Hilt modules: shared ones in `:core:data/di/`, a feature's own in its `di` package |
| `android-presentation-layer.mdc` | MVI (`UiState` / `UiAction` / `UiEvent`), Route vs Screen, previews, Paging3 |
| `android-navigation.mdc` | Navigation3 routes, NavEntries, graph wiring |
| `android-design-system.mdc` | Design tokens, no hardcoded values, foundational components |
| `android-base-components.mdc` | Canonical base classes (`Resource`, `UiState`, `BaseRemoteDataSource`, `BasePagingSource`, ...) and their templates in `.cursor/templates/` |
| `android-unit-tests.mdc` | Kotest `DescribeSpec` + MockK |
| `android-ui-tests.mdc` | JUnit 4 + `ComposeTestRule` |
| `git-commit.mdc` | Commit format (only when the user asks to commit) |
| `generate-release-notes.mdc` | Play Console release notes (only when asked) |

---

# Part A: Project-specific (rewrite per project)

This part records the project's values and the decisions the user already made: module list (A2), navigation (A3), UI reference (A4), visual language (A5) and build and domain notes (A6). Anything they don't cover follows the rules, and any conflict goes to the user.

## A1. Project Profile

| Key | Value |
|---|---|
| App | Movies Explorer: browse movies and TV shows from the [TMDB API](https://developer.themoviedb.org/docs) |
| `<basePackage>` | `dev.brunofelix.movies`. Source packages: `dev.brunofelix.movies.<domain\|data\|presentation\|designsystem>`, and every feature uses `dev.brunofelix.movies.presentation` (see Package Structure in `android-architecture.mdc`). Gradle namespaces, which only name `R` and `BuildConfig`: `dev.brunofelix.movies.<core\|feature>.<name>`. `:app` uses `dev.brunofelix.movies` for both |
| Application ID / launch activity | `dev.brunofelix.movies` (`prod`; `beta` adds `.beta`) / `dev.brunofelix.movies/.MainActivity` |
| Build variant | Flavors `prod` and `beta` in `:app` (libraries have no flavors). Use `prodDebug` unless told otherwise; task names are in A6 |
| Theme composable (`AppTheme`) | `PMovieTheme` |
| Theme mode | Dark only (`DarkColorScheme` in `Theme.kt`; `MyApplication` forces night mode) |
| Font family token (`AppFontFamily`) | `UrbanistFontFamily` (Urbanist regular, medium, bold) |
| Design source / tokens doc | None (see A4) |
| Spacing, size, elevation and shape tokens | `spacing{X}`, `size{X}`, `elevation{X}`, `shapeCircle`, `shapeRounded{X}` and `AppShapes`, all in `Shapes.kt` |
| Foundational components | `CustomButton`, `CustomSearchBar`, `SelectorChip` (`SelectorTopSpacing`, `SelectorContentSpacing`), `SectionCard`, `GradientBackground` (`AppGradient`, `SplashGradient`), `MainTopBar`, `SecondaryTopBar`, `DetailTopBar`, `DetailStatusLayout`, `DetailSkeleton`, `LoadingState`, `EmptyState`, `EmptyImage`, `PagingRetry`, `MovieInfoChip`, `Modifier.shimmerEffect` |
| Shared presentation components | In `:core:presentation/components`: `MediaCard`, `MediaSection`, `MainContent` (paged grid), `CastSection`, `MovieOverview`, `MovieGenderContainer`, `CategorySelector`, `ErrorLayout`, `CustomNavBar`, `YouTubePlayer` |
| Brand tokens without a Material role | `DarkRed`, `RatingStar`, `IconSilver`, `ShimmerBase`, `ShimmerHighlight`, `SurfaceGlass*`, `OutlineSubtle`, `OutlineMedium`, `Scrim*` (in `Color.kt`) |
| Project-specific stack | TMDB REST API (Retrofit + Kotlinx Serialization, client in `:core:data`); DataStore Preferences (language); Paging 3; android-youtube-player (trailers, in `:core:presentation`); Timber (`:app`); Splash Screen API |
| Secrets | `apiKey.properties` at the root (`API_KEY`, `BASE_URL`, `BASE_URL_IMAGE`) is read by `:core:data` into its `BuildConfig`; never commit it. `keystore.properties` is optional (release signing in `:app`) |
| Localization | English (`values/`, default), Portuguese (`values-pt-rBR/`), Spanish (`values-es/`). Each module owns its strings, and every new string goes into all three |

## A2. Modules

| Module | Contents |
|---|---|
| `:app` | `MyApplication`, `MainActivity`, `ui/MainScreen` (tabs top bar, bottom bar and search overlay), `navigation/NavigationGraph`, launcher resources |
| `:core:domain` | Models, repository interfaces, the shared use cases (`DeleteMediaUseCase`, `GetLanguageUseCase`, `IsFavoriteMediaUseCase`, `SaveMediaUseCase`), `Resource` and its extensions, exceptions, `DateTimeConverter` |
| `:core:data` | DTOs, `MovieApi` / `TvShowApi`, Room (`AppDatabase`, `MediaDao`), DataStore, data sources, repositories, the shared use case implementations, Hilt modules (`RemoteModule`, `LocalModule`, `DataSourceModule`, `RepositoryModule`, `UseCaseModule`) |
| `:core:presentation` | Base components (`UiState`, `UiText`, `ObserveAsEvents`, `BasePagingSource`, paging and error extensions), `Route`, `NavigationViewModel`, UI models and mappers, shared components |
| `:core:designsystem` | Theme tokens and foundational components |
| `:feature:splash` | Splash destination (no ViewModel) |
| `:feature:movie`, `:feature:tv_show` | Home tab and details of each media type |
| `:feature:favorite`, `:feature:release` | Favorites and release calendar tabs |
| `:feature:media_list` | Full, paginated version of a home row |
| `:feature:settings` | Language and app information |
| `:feature:search` | Search overlay. It is not a destination: `MainScreen` hosts `SearchOverlayRoute` over the tabs |

Every feature except `:feature:splash` owns the use cases that only it calls: interfaces in `domain/use_case`, implementations in `data/use_case`, bindings in `di/<Feature>UseCaseModule` (e.g., `MovieUseCaseModule`). The repositories they call stay in `:core:domain` / `:core:data`.

## A3. Navigation

- `Route` and `NavigationViewModel` are the templates. The back stack starts at `Route.Splash`, which replaces itself with `Route.Movies`.
- The four tabs (`TopLevelRoutes`: Movies, TV Shows, Favorites, Releases) are routes. The bottom bar only shows over a tab root, so switching tabs uses `replaceCurrent`; everything else is `navigateTo`.
- Each feature exposes one `<feature>NavEntry` with navigation lambdas (`onNavigateToDetails: (Media) -> Unit`, `onBack`, ...). `NavigationGraph` maps them to routes (`Media.toDetailRoute()`) and passes the scaffold insets to the tabs.
- `NavDisplay` uses the saveable state holder and ViewModel store decorators, so each entry has its own ViewModel; pops use the crossfade in `NavigationGraph`.

## A4. UI Reference

There is no design source. Don't use Stitch (the "Movies (Compose)" project there is outdated), Figma, or a `DESIGN.md` for this app. The reference is the current Compose code and the screenshots in `screenshots/` (shown in `README.md`).
- Changing an existing screen: keep its layout and visual language unless the request says otherwise.
- New screen, or a layout the request doesn't describe: ask the user for a description or reference image before implementing. Never guess a layout.
- Translate every value into a token from A1, never a literal. If none matches, add one following `android-design-system.mdc` and use it.

## A5. Visual Language

- Flat black canvas: screens sit on `GradientBackground` with `AppGradient`. `SplashGradient` (black into `DarkRed`) is reserved for the splash.
- `MaterialTheme.colorScheme.primary` (red) is the single accent: selected tab, primary buttons, loaders, favorite icon, info chip icons. Text uses `onBackground`; secondary text and outlines `onSurfaceVariant`; image placeholders `surfaceVariant`.
- All text uses Urbanist through `MaterialTheme.typography`.
- Home tabs show horizontal rows of rounded poster cards (`MediaSection` / `MediaCard`) under `MainTopBar` (search and settings), with `CustomNavBar` at the bottom.
- Movie and TV show details follow an Apple TV+ style: a full-bleed backdrop with parallax, content grouped in `SectionCard`s, and a transparent `DetailTopBar` whose title fades in as the user scrolls (`scrollFraction`).

## A6. Domain and Build Notes

- **TMDB requests**: `RemoteInterceptor` adds `api_key` and `language` (from `LanguageRepository`) to every request; never add them per endpoint. Image paths become absolute URLs in the DTO mappers.
- **Pagination**: TMDB lists paginate by page number with 20 items per page, so `BasePagingSource` applies. Search pages hold the movies and the TV shows of the same TMDB page, so its page size is 40.
- **Favorites** are stored in Room through `MediaRepository`; one-shot local operations return `Resource` and the screens report failures with a toast.
- **Languages**: `LanguageEnum` (`en`, `pt-BR`, `es`) drives both the TMDB `language` parameter and the app locale. Adding one means new `values-*` folders and an entry in `res/xml/locales_config.xml`.
- **Room tests**: `MediaDao` and `AppDatabase` are tested as instrumented tests in `:core:data/src/androidTest` (JUnit 4 + Kotest matchers), since the Kotest Robolectric extension is archived.
- **Gradle tasks**: because of the flavors, the Part B commands map to the ones below.

| Part B | Here |
|---|---|
| `./gradlew assembleDebug` | Same (builds both flavors); `./gradlew :app:assembleProdDebug` builds only `prod` |
| `./gradlew testDebugUnitTest` | Same for the library modules; add `:core:domain:test` (pure Kotlin) and `:app:testProdDebugUnitTest` |
| `./gradlew connectedDebugAndroidTest` | Same for the library modules; `:app:connectedProdDebugAndroidTest` for the app |
| `./gradlew installDebug` | `./gradlew :app:installProdDebug` |

---

# Part B: Standard workflow (same in every project)

## B1. Mandatory Test-Driven Generation (TDD)
You **MUST NEVER** create a new feature, component, or logic class without creating its test file in the same task. When you create or modify any of the following, you are **OBLIGATED** to write the matching Unit Test (`src/test`) or UI Test (`src/androidTest`):

### Data & Domain Layers (Unit Tests, `android-unit-tests.mdc`)
- **Repositories** (`*RepositoryImpl.kt`)
- **Use Cases** (`*UseCaseImpl.kt`)
- **Data Sources** (`*LocalDataSourceImpl.kt`, `*RemoteDataSourceImpl.kt`)
- **API Services / DAOs** (`*Api.kt`, `*Dao.kt`)
- **Mappers** (`*DtoMapper.kt`, `*EntityMapper.kt`, `*Mapper.kt`)
- **Utils & Extensions** (`*Ext.kt`, utility classes)
- **Databases** (`*Database.kt`)

### Presentation Layer (`android-unit-tests.mdc` + `android-ui-tests.mdc`)
- **ViewModels** (`*ViewModel.kt`) → Unit test covering `UiState` transitions and emitted `UiEvent`s.
- **Screens** (`*Screen.kt`) → UI test of the stateless Screen with mock `UiState`s, never the Route.
- **Reusable Components** (`*Item.kt`, `*Bar.kt`, `*Card.kt`, anything in `presentation/components` or `:core:designsystem/components`) → UI test for rendering and click callbacks.
- **Navigation** (`NavigationGraph.kt`, `*NavEntry.kt`) → UI test that each route renders the right screen.
- **Controllers** (`*ControllerImpl.kt`) → Unit or UI test depending on framework dependencies.

## B2. Execution Workflow
When asked to create a new feature, work in this order:
1. **Domain**: models, repository interfaces, use case `fun interface`s. Code only the new feature uses goes in its `domain` package; shared code goes in `:core:domain`. Test any logic that lives here (e.g. extensions in `/util`).
2. **Data**: DTOs/entities, mappers, data sources, repository and use case implementations, Hilt bindings. Implementations of the feature's own contracts go in its `data` package and are bound in its `di` package; shared ones (and anything Room-related) go in `:core:data`, bound in `:core:data/di/`. → Mapper, data source, repository, and use case tests.
3. **Presentation** (`:feature:<name>`, package `presentation`): `UiState` / `UiAction` / `UiEvent`, ViewModel, Route + stateless Screen, components, previews. → ViewModel unit tests and Screen/component UI tests.
4. **Navigation**: add the `Route`, create `<feature>NavEntry`, wire it into `NavigationGraph` in `:app`. → Navigation UI test.
5. **Verify**: run `./gradlew assembleDebug` and `./gradlew testDebugUnitTest`. A task is never "done" until its tests exist and pass. If something can't be run (e.g. no device for `connectedDebugAndroidTest`), say so explicitly.

Before using Hilt, Navigation3, or Kotest in a module, check that it is already configured in `gradle/libs.versions.toml` and the module's `build.gradle.kts`. If it isn't, tell the user that setting it up is part of the task instead of silently mixing patterns.

## B3. Jetpack Compose Previews
- Every new or modified composable (screen or component) gets at least one `@Preview` with mock data, wrapped in the theme composable from the Project Profile.
- Stateless `<Feature>Screen`s get one preview per possible `UiState` (Loading, Success, Empty, Error).

## B4. Git Workflow
- **No automatic commits**: you MUST NEVER run `git commit` (or push) on your own. Only stage or commit when the user explicitly asks (e.g., "commit these changes").
- When asked to commit, follow `git-commit.mdc` (gitmoji + Conventional Commits, split by feature/layer).

## B5. Build Commands
- **Compile Debug APK**: `./gradlew assembleDebug`
- **Run Unit Tests**: `./gradlew testDebugUnitTest`
- **Run UI Tests**: `./gradlew connectedDebugAndroidTest`
- **Install & Run on Device**: `./gradlew installDebug`, then `adb shell am start -n <launch activity from the Project Profile>`.
